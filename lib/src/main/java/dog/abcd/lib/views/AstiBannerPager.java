package dog.abcd.lib.views;

import java.util.ArrayList;
import java.util.List;

import dog.abcd.lib.utils.AntiImageLoader;
import dog.abcd.lib.utils.AntiScreenUtils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import dog.abcd.lib.R;

/**
 * 
 * <b>循环轮播</b><br>
 * 先调用init方法初始化，再通过setBannerUrlList设置图片链接列表，最后setOnBannerClickListener设置监听事件
 * 
 * @author Michael Lee<br>
 *         <b> create at </b>2016-5-20 上午10:57:41
 */
public class AstiBannerPager extends RelativeLayout {

	public AstiBannerPager(Context context) {
		this(context,null);
	}

	public AstiBannerPager(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public AstiBannerPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

    private int circleBottomMargin = 10;
    private int circleSize = 8;
    private int circleBg = R.drawable.bg_circle_banner;
    private int circleBgOn = R.drawable.bg_circle_banner_on;

	private OnBannerClickListener onBannerClickListener;

	private ViewPager vPager;
	private LinearLayout lLayout;
	/**
	 * 下面的几个点
	 */
	private List<ImageView> listImageView = new ArrayList<ImageView>();
	/**
	 * 图片链接列表
	 */
	private List<String> listBannerUrl = new ArrayList<String>();

	private boolean isFirst = true;
	private int pagerIndex = 0;

	/**
	 * 最先调用这个方法初始化控件
	 */
	public void init() {
		vPager = new ViewPager(getContext());
		lLayout = new LinearLayout(getContext());
		LayoutParams paramsPager = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		LayoutParams paramsLayout = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		paramsLayout.addRule(ALIGN_PARENT_BOTTOM);
		paramsLayout.bottomMargin = AntiScreenUtils.dp2Px(getContext(),circleBottomMargin);
		lLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        vPager.addOnPageChangeListener(new MyOnPageChangeListener());
		this.addView(vPager, paramsPager);
		this.addView(lLayout, paramsLayout);
	}

	/**
	 * 设置图片链接列表
	 * 
	 * @param list
	 */
	public void setBannerUrlList(List<String> list) {
		stopScrollPager();
		vPager.removeAllViews();
		this.lLayout.removeAllViews();
		// 清空ImageView的内存占用
		for (ImageView iv : listImageView) {
			iv.setImageBitmap(null);
		}
		listImageView.clear();
		// 把最后一张加到第一张，第一张加到最后一张
		if (list.size() > 1) {
			listBannerUrl.clear();
			listBannerUrl.add(list.get(list.size() - 1));
			listBannerUrl.addAll(list);
			listBannerUrl.add(list.get(0));
		} else if (list.size() == 1) {
			listBannerUrl.clear();
			listBannerUrl.addAll(list);
			setOnlyOne();
			return;
		} else {
			listBannerUrl.clear();
			return;
		}

		// 设置ViewPager页面缓存数量与图片数量相同，在第一页和最后一页切换的时候才不会闪，要极致性能可以去掉
		try {
			vPager.setOffscreenPageLimit(listBannerUrl.size());
		} catch (Exception e) {

		}
		// 从第二张开始显示
		pagerIndex = 1;

		// 这里是圆点
		LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(AntiScreenUtils.dp2Px(getContext(), circleSize),
				AntiScreenUtils.dp2Px(getContext(), circleSize));
		imageParams.leftMargin = AntiScreenUtils.dp2Px(getContext(), circleBottomMargin);
		// 添加圆点的时候少添加两个
		listImageView.clear();
		for (int i = 0; i < listBannerUrl.size() - 2; i++) {
			listImageView.add(new ImageView(getContext()));
			this.lLayout.addView(listImageView.get(i), imageParams);
            final int curIndex = i+1;
            listImageView.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    vPager.setCurrentItem(curIndex);
                }
            });
		}
		List<ImageView> imageList = new ArrayList<ImageView>();
		// 从网络获取图片并生成ImageView对象
		for (int i = 0; i < listBannerUrl.size(); i++) {
			String url = listBannerUrl.get(i);
			ImageView iv = new ImageView(getContext());
			iv.setScaleType(ScaleType.FIT_XY);
			AntiImageLoader.getInstance().display(url, iv);
			// 确保回调方法中的索引正确
			if (i > 0 && i < listBannerUrl.size() - 1) {
				iv.setTag(i - 1);
			} else if (i == 0) {
				iv.setTag(listBannerUrl.size() - 3);
			} else {
				iv.setTag(0);
			}
			iv.setBackgroundResource(R.drawable.bg_btn_white);
			imageList.add(iv);
		}
		BannerAdapter adapter = new BannerAdapter(imageList);
		vPager.setAdapter(adapter);
		// 点从0开始
		currentDot(0);
		// 从1开始
		vPager.setCurrentItem(1, false);
		if (isFirst) {
			startScrollPager();
			isFirst = !isFirst;
		}
	}

	private void setOnlyOne() {
		LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(AntiScreenUtils.dp2Px(getContext(), 8),
				AntiScreenUtils.dp2Px(getContext(), 8));
		imageParams.leftMargin = 20;
		// 添加一个圆点
		listImageView.clear();
		listImageView.add(new ImageView(getContext()));
		this.lLayout.addView(listImageView.get(0), imageParams);
		currentDot(0);
		List<ImageView> imageList = new ArrayList<ImageView>();
		// 从网络获取图片并生成ImageView对象
		String url = listBannerUrl.get(0);
		ImageView iv = new ImageView(getContext());
		iv.setScaleType(ScaleType.FIT_XY);
		AntiImageLoader.getInstance().display(url, iv);
		iv.setTag(0);
		iv.setBackgroundResource(R.drawable.bg_btn_white);
		imageList.add(iv);
		BannerAdapter adapter = new BannerAdapter(imageList);
		vPager.setAdapter(adapter);
	}

	private Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (listBannerUrl.size() <= 1) {
				return;
			}
			pagerIndex++;
			// 切换ViewPager的时候在监听里面在去调用方法切换点
			vPager.setCurrentItem(pagerIndex);
		}
	};

	public void startScrollPager() {
		stopScrollPager();
		handler.postDelayed(runnable, 3000);
	}

	public void stopScrollPager() {
		handler.removeCallbacks(runnable);
	}

	/**
	 * 切换下面的点
	 */
    private void currentDot(int index) {
		if (listImageView.size() == 0) {
			return;
		}
		index = index % (listImageView.size());
		for (int i = 0; i < listImageView.size(); i++) {
			// 设置默认的样式
			listImageView.get(i).setBackgroundResource(circleBg);
		}
		// 设置当前位置的点的样式
		listImageView.get(index).setBackgroundResource(circleBgOn);
	}

	/**
	 * 页面切换的监听
	 */
    private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if (arg1 != 0) {
				return;
			}
			if (listBannerUrl.size() <= 1) {
				return;
			}
			if (arg0 == 0) {
				// 切换到第一张了，跳转到最后一张
				pagerIndex = listBannerUrl.size() - 1;
				vPager.setCurrentItem(listBannerUrl.size() - 2, false);
			} else if (arg0 > 0 && arg0 < listBannerUrl.size() - 1) {
				// 中间
				stopScrollPager();
				startScrollPager();
				pagerIndex = vPager.getCurrentItem();
			} else if (arg0 == listBannerUrl.size() - 1) {
				// 最后一张，跳转到第一张
				pagerIndex = 1;
				vPager.setCurrentItem(1, false);
			} else {
				// 日了狗才会调用这里
			}
		}

		@Override
		public void onPageSelected(int arg0) {
			if (listBannerUrl.size() <= 1) {
				return;
			}
			if (arg0 == 0) {
				// 第一张，显示最后一张
				currentDot(listImageView.size() - 1);
			} else if (arg0 > 0 && arg0 < listBannerUrl.size() - 1) {
				// 中间
				currentDot(vPager.getCurrentItem() - 1);
			} else if (arg0 == listBannerUrl.size() - 1) {
				// 最后一张，显示第一张
				currentDot(0);
			} else {
				// 日了狗才会调用这里
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 绑定ViewPager
	 * 
	 * @author 李其鹏
	 * 
	 */
	class BannerAdapter extends PagerAdapter {

		private List<ImageView> listImage = new ArrayList<ImageView>();

		public BannerAdapter(List<ImageView> list) {
			// 给listImage生成ImageView的对象
			listImage = list;
		}

		@Override
		public int getCount() {
			return listImage.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = listImage.get(position);
			container.removeView(view);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			listImage.get(position).setOnClickListener(imageOnclick);
			if (listImage.get(position).getParent() != null) {
				ViewGroup vg = (ViewGroup) listImage.get(position).getParent();
				vg.removeView(listImage.get(position));
			}
			AntiImageLoader.getInstance().display(listBannerUrl.get(position), listImage.get(position));
			container.addView(listImage.get(position), 0);
			return listImage.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	public OnBannerClickListener getOnBannerClickListener() {
		return onBannerClickListener;
	}

    /**
     * 设置图片点击的监听
     * @param onBannerClickListener
     */
	public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
		this.onBannerClickListener = onBannerClickListener;
	}

	OnClickListener imageOnclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Integer position = (Integer) arg0.getTag();
			if (onBannerClickListener != null) {
				onBannerClickListener.onClick(position);
			}
		}

	};

	public interface OnBannerClickListener {
		public abstract void onClick(int position);
	}


    /**
     * 获取中间滚动图片
     * @return
     */
    public ViewPager getPager(){
        return vPager;
    }

    /**
     * 获取下面装圆点的部分
     * @return
     */
    public LinearLayout getLayout(){
        return  lLayout;
    }


    /**
     * 设置圆点到底部的间隔（不设置则按照默认显示）
     * @param circleBottomMargin 单位为dp
     */
    public void setCircleBottomMargin(int circleBottomMargin){
        this.circleBottomMargin = circleBottomMargin;
    }

    /**
     * 设置圆点的大小（不设置则按照默认显示）
     * @param circleSize 单位为dp
     */
    public void setCircleSize(int circleSize){
        this.circleSize = circleSize;
    }

    /**
     * 设置圆点未选中时的背景（不设置则按照默认显示）
     * @param circleBg 资源ID
     */
    public void setCircleBg(int circleBg){
        this.circleBg = circleBg;
    }

    /**
     * 设置圆点选中后的背景（不设置则按照默认显示）
     * @param circleBgOn 资源ID
     */
    public void setCircleBgOn(int circleBgOn){
        this.circleBgOn = circleBgOn;
    }

    /**
     * 初始化显示（不设置则按照默认显示）
     * @param circleBottomMargin 单位为dp
     * @param circleSize 单位为dp
     * @param circleBg 资源ID
     * @param circleBgOn 资源ID
     */
    public void initStyle(int circleBottomMargin,int circleSize,int circleBg,int circleBgOn){
        this.circleBg = circleBg;
        this.circleBgOn = circleBgOn;
        this.circleBottomMargin = circleBottomMargin;
        this.circleSize = circleSize;
    }
}
