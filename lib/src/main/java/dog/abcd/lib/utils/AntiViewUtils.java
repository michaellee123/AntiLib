package dog.abcd.lib.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * <b> 对View显示的操作</b><br>
 * 一些计算高度和UI显示的操作
 * 
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 下午1:54:42
 */
public class AntiViewUtils {

	/**
	 * 给文字设置删除线
	 * 
	 * @param tView
	 */
	public static void setDeleteLine(TextView tView) {
		tView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		tView.getPaint().setAntiAlias(true);// 抗锯齿
	}

	/**
	 * 给文字设置下划线
	 * 
	 * @param tView
	 */
	public static void setUnderLine(TextView tView) {
		tView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tView.getPaint().setAntiAlias(true);// 抗锯齿
	}

	/**
	 * 给文字设置粗体
	 * 
	 * @param tView
	 */
	public static void setBoldText(TextView tView) {
		tView.getPaint().setFakeBoldText(true);
	}

	/**
	 * 设置ListView按照item来显示的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View view = listAdapter
					.getView(i, listView.getChildAt(i), listView);
			view.measure(0, 0);
			totalHeight += view.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);
	}

	/**
	 * 设置GridView根据item来显示高度
	 * 
	 * @param gridView
	 * @param cols
	 *            每一行的个数
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void setGridViewHeightBasedOnChildren(GridView gridView,
			int cols) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		try {
			View view = listAdapter
					.getView(0, gridView.getChildAt(0), gridView);
			view.measure(0, 0);
			totalHeight = view.getMeasuredHeight();
		} catch (Exception e) {
		}
		int lines = (listAdapter.getCount() + (cols - 1)) / cols;
		int padding = 0;
		try {
			padding = gridView.getVerticalSpacing();
		} catch (Exception e) {

		}
		int totalPadding = padding * (lines + 1);
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight * lines + totalPadding;

		gridView.setLayoutParams(params);
	}

	/**
	 * 让一个输入框只能输入两位小数
	 * 
	 * @param editText
	 */
	public static void setPricePoint(final EditText editText) {
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0,
								s.toString().indexOf(".") + 3);
						editText.setText(s);
						editText.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					editText.setText(s);
					editText.setSelection(2);
				}

				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						editText.setText(s.subSequence(0, 1));
						editText.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

		});

	}

	/**
	 * 设置一个输入框输入的东西全部变成大写
	 * 
	 * @param et
	 */
	public static void setEditTextUpcase(EditText et) {
		et.setTransformationMethod(new AllCapTransformationMethod());
	}

	static class AllCapTransformationMethod extends
			ReplacementTransformationMethod {

		@Override
		protected char[] getOriginal() {
			char[] aa = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
					'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
					'w', 'x', 'y', 'z' };
			return aa;
		}

		@Override
		protected char[] getReplacement() {
			char[] cc = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
					'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
					'W', 'X', 'Y', 'Z' };
			return cc;
		}

	}

	/**
	 * 图片宽度为match的时候获取应该显示的高度
	 * 
	 * @param bitmap
	 * @return
	 */
	public static int getBitmapHeight(Bitmap bitmap, Context context) {
		float bitmapWidth = (float) bitmap.getWidth();
		float bitmapHeight = (float) bitmap.getHeight();
		float screenWidth = (float) AntiScreenUtils.getScreenWidth(context);
		return (int) (bitmapHeight * (screenWidth / bitmapWidth));
	}

	/**
	 * 设置输入框最长输入长度
	 * 
	 * @param editText
	 *            输入框
	 * @param maxLength
	 *            长度
	 */
	public static void setEditTextMaxLength(EditText editText, int maxLength) {
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				maxLength) });
	}

	/**
	 * 设置输入框输入银行卡号码
	 * 
	 * @param editText
	 */
	public static void setEditBankcard(final EditText editText) {
		editText.addTextChangedListener(new TextWatcher() {
			int beforeTextLength = 0;
			int onTextLength = 0;
			boolean isChanged = false;

			int location = 0;// 记录光标的位置
			private char[] tempChar;
			private StringBuffer buffer = new StringBuffer();
			int konggeNumberB = 0;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				onTextLength = s.length();
				buffer.append(s.toString());
				if (onTextLength == beforeTextLength || onTextLength <= 3
						|| isChanged) {
					isChanged = false;
					return;
				}
				isChanged = true;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				beforeTextLength = s.length();
				if (buffer.length() > 0) {
					buffer.delete(0, buffer.length());
				}
				konggeNumberB = 0;
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == ' ') {
						konggeNumberB++;
					}
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (isChanged) {
					location = editText.getSelectionEnd();
					int index = 0;
					while (index < buffer.length()) {
						if (buffer.charAt(index) == ' ') {
							buffer.deleteCharAt(index);
						} else {
							index++;
						}
					}

					index = 0;
					int konggeNumberC = 0;
					while (index < buffer.length()) {
						if ((index == 4 || index == 9 || index == 14 || index == 19)) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
						index++;
					}

					if (konggeNumberC > konggeNumberB) {
						location += (konggeNumberC - konggeNumberB);
					}

					tempChar = new char[buffer.length()];
					buffer.getChars(0, buffer.length(), tempChar, 0);
					String str = buffer.toString();
					if (location > str.length()) {
						location = str.length();
					} else if (location < 0) {
						location = 0;
					}

					editText.setText(str);
					try {
						Editable etable = editText.getText();
						Selection.setSelection(etable, location);
					} catch (Exception e) {
						Editable etable = editText.getText();
						Selection.setSelection(etable, etable.toString()
								.length());
					}
					isChanged = false;

				}
			}

		});
	}

	/**
	 * 设置输入框不能输入emoji表情
	 * @param editText
     */
	public static void setEditUnemojiable(final EditText editText){
		editText.addTextChangedListener(new TextWatcher() {

			private int cursorPos;
			private String inputAfterText;
			private boolean resetText;
			private Context context;

			public int getCursorPos() {
				return cursorPos;
			}

			public String getInputAfterText() {
				return inputAfterText;
			}

			public boolean isResetText() {
				return resetText;
			}

			public Context getTheContext() {
				return context;
			}


			@Override
			public void beforeTextChanged(CharSequence s, int start,
										  int before, int count) {
				if (!resetText) {
					cursorPos = editText.getSelectionEnd(); // 这里用s.toString()而不直接用s是因为如果用s，
					// 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
					// inputAfterText也就改变了，那么表情过滤就失败了
					inputAfterText = s.toString();
				}

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				try {
					if (!resetText) {
						if (count >= 2) {// 表情符号的字符长度最小为2
							CharSequence input = s.subSequence(cursorPos,
									cursorPos + count);
							if (containsEmoji(input.toString())) {
								resetText = true;
								AntiToast.show(context, "不支持卖萌");
								// 是表情符号就将文本还原为输入表情符号之前的内容
								editText.setText(inputAfterText);
								CharSequence text = editText.getText();
								if (text instanceof Spannable) {
									Spannable spanText = (Spannable) text;
									Selection.setSelection(spanText,
											text.length());
								}
							}
						}
					} else {
						resetText = false;
					}
				} catch (Exception e) {

				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}

			/**
			 * 检测是否有emoji表情
			 *
			 * @param source
			 * @return
			 */
			public boolean containsEmoji(String source) {
				int len = source.length();
				for (int i = 0; i < len; i++) {
					char codePoint = source.charAt(i);
					if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
						return true;
					}
				}
				return false;
			}

			/**
			 * 判断是否是Emoji
			 *
			 * @param codePoint 比较的单个字符
			 * @return
			 */
			private boolean isEmojiCharacter(char codePoint) {
				return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
						|| (codePoint == 0xD)
						|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
						|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
						|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
			}

		});
	}

}
