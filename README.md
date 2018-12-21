最近在开发一个朋友圈产品的时候遇到一个bug：软键盘遮罩，在解决的过程中我通过百度、谷歌查找了好半天，最终经历了一番坎坷才解决，具体过程且听我娓娓道来！

### 一、windowSoftInputMode 

这个是在遇到软键盘相关的问题，脑子里第一个想到的知识点，但是效果如何呢？能解决问题，但是不完美！
先看没有解决的效果图：


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce5dccad194b1?w=540&h=960&f=png&s=153352)

就是说当一个列表的最后一项需要输入时，软键盘会将EditText完全遮罩！
其实将就一下这个效果也可以，但是我们可以选择不将就——死磕到底！因此我们来看看 windowSoftInputMode 怎么解决这个问题？


<table>
        <tr>
            <th colspan="3">Activity获取焦点后，软键盘隐藏与显示</th>
        </tr>
        <tr>
            <th>stateUnspecified</th>
            <th>不指定软键盘的状态</th>
            <th>（隐藏还是可见） 将由系统选择合适的状态，或依赖主题中的设置，这是对软键盘行为的默认设置</th>
        </tr>
        <tr>
            <th>stateUnchanged</th>
            <th>保留状态</th>
            <th>当 Activity 转至前台时保留软键盘最后所处的任何状态，无论是可见还是隐藏</th>
        </tr>
        <tr>
            <th>stateHidden</th>
            <th>隐藏软键盘</th>
            <th>当用户确实是向前导航到 Activity，而不是因离开另一Activity 而返回时隐藏软键盘</th>
        </tr>
        <tr>
            <th>stateAlwaysHidden</th>
            <th>始终隐藏软键盘</th>
            <th>当 Activity 的主窗口有输入焦点时始终隐藏软键盘</th>
        </tr>
        <tr>
            <th>stateVisible</th>
            <th>显示软键盘</th>
            <th>在正常的适宜情况下（当用户向前导航到 Activity 的主窗口时）显示软键盘</th>
        </tr>
        <tr>
            <th>stateAlwaysVisible</th>
            <th>始终显示软键盘</th>
            <th>当用户确实是向前导航到 Activity，而不是因离开另一Activity 而返回时显示软键盘</th>
        </tr>
        <tr >
    <th colspan="3">在软键盘弹出时，Activity调整策略</th>
   </tr>
    <tr>
            <th>adjustUnspecified </th>
            <th colspan="2">主窗口的默认行为，不指定 Activity 的主窗口是否调整尺寸以为软键盘腾出空间，或者窗口内容是否进行平移以在屏幕上显露当前焦点。 系统会根据窗口的内容是否存在任何可滚动其内容的布局视图来自动选择其中一种模式。 如果存在这样的视图，窗口将进行尺寸调整，前提是可通过滚动在较小区域内看到窗口的所有内容。</th>
        </tr>
        <tr>
            <th>adjustResize</th>
            <th colspan="2">始终调整 Activity 主窗口的尺寸来为屏幕上的软键盘腾出空间。当软键盘弹出时，会让布局重新绘制，这种一般适应于带有滑动性质的控制，让其向下滚动，然后适应软键盘的显示。</th>
        </tr>
           <tr>
            <th>adjustPan </th>
            <th colspan="2">不调整 Activity 主窗口的尺寸来为软键盘腾出空间， 而是自动平移窗口的内容，使当前焦点永远不被键盘遮盖，让用户始终都能看到其输入的内容。 这通常不如尺寸调整可取，因为用户可能需要关闭软键盘以到达被遮盖的窗口部分或与这些部分进行交互。</th>
        </tr>
        <tr>
            <th>adjustNoting </th>
            <th colspan="2">软键盘弹出时，主窗口Activity不会做出任何响应。</th>
        </tr>
    </table>

上面的表格说明了两个问题：软键盘显示与Activity响应策略。在上面的项目中，软键盘显示是没有问题的，只是Activity的部分内容被遮罩，可以调整策略解决的。那么我们来依次尝试一下这些个响应策略！

 * stateUnspecified：
 

![](https://user-gold-cdn.xitu.io/2018/12/21/167ce62b3d8898ce?w=540&h=960&f=png&s=153542)

默认的策略一进来软键盘就将底部遮挡了，我们都无法操作底部的 item ,因此我们需要进来时不显示软键盘，增加一个策略

```
	android:windowSoftInputMode="stateHidden|stateUnspecified"
```
现在进来倒是不显示了，但是点击底部的item时还是一样会被遮挡：


 
![](https://user-gold-cdn.xitu.io/2018/12/21/167ce636ffb0d192?w=540&h=960&f=png&s=153352)

  * adjustPan：
 
```
	android:windowSoftInputMode="stateHidden|stateUnspecified"
```


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce6433e3cb0b9?w=540&h=960&f=png&s=134180)

adjustPan 策略确实将 Activity 主窗口平移上去了，但是我的 Title 部分也平移上去了！这就是我说的不完美的地方，那么我们试一下主窗口重绘呢？

 * adjustResize ：

```
	android:windowSoftInputMode="stateHidden|adjustResize"
```




![](https://user-gold-cdn.xitu.io/2018/12/21/167ce650f78f46d2?w=540&h=960&f=png&s=153352)

adjustResize 策略并没有起到作用，底部的输入界面依然被遮罩了，这里我只能接受 adjustPan 策略了！但是还有一个 adjustNoting 策略看看会不会是一样？既然死磕，咱们就一个一个都试个遍！

 * adjustNoting 

```
	android:windowSoftInputMode="stateHidden|adjustNothing"
```

很好，果然没有令我们失望，确实是不行！


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce6578c2091bc?w=540&h=960&f=png&s=153352)

而 ConstraintLayout、RelativeLayout 以及 FrameLayout 布局将 EditText 置于布局底部测试默认是正常的。


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce664e247eff6?w=915&h=604&f=png&s=26766)

但是为什么微信聊天页面使用 RecyclerView 布局的效果不是这样的啊？为此我联系到了一个仿朋友圈的大神，他告诉了我第二种方法：动态计算软键盘的高度

### 二、动态计算软键盘高度

动态计算软键盘的高度这一块，我们增加一个难度，就是增加软键盘与 EditText 之间的间距，说起来抽象，还是上图：


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce671c582f23f?w=543&h=897&f=png&s=211521)

至于为什么要增加难度，还不是产品要求……既然人家能实现，咱也努把力实现呗！由于动态计算软键盘高度这件事，咱们不需要再设置 SoftInputMode 了，因为整个过程纯手工操作，不需要系统其它 api 支持了！

 1. 首先，我们需要做一些准备工作，将软键盘与主页内容剥离，主页内容就是一个 RecyclerView ，软键盘部分是一个布局包含的 EditText ，软键盘布局如图：


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce678df80ed58?w=724&h=596&f=png&s=21721)

2. 将上面的软键盘进行封装，这个是重点。说起来比较抽象，就上一张流程图和代码：


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce6816b3a2fa9?w=923&h=747&f=png&s=49315)




```
	public class EmojiPanelView extends LinearLayout implements OnKeyBoardStateListener {
	···
	 public EmojiPanelView(Context context) {
        super(context);
        init();
    }
     private void init() {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_emoji_panel, this, false);
        mEditText = itemView.findViewById(R.id.edit_text);
        mEditText.setOnTouchListener((v, event) -> {
            showSoftKeyBoard();
            return true;
        });

        mImageSwitch = itemView.findViewById(R.id.img_switch);
        mImageSwitch.setOnClickListener(v -> {
            if (isKeyBoardShow) {
                mImageSwitch.setImageResource(R.drawable.input_keyboard_drawable);
                changeLayoutNullParams(false);
                hideSoftKeyBoard();
                changeEmojiPanelParams(mKeyBoardHeight);
            } else {
                mImageSwitch.setImageResource(R.drawable.input_smile_drawable);
                showSoftKeyBoard();
            }
        });
        ···
        addOnSoftKeyBoardVisibleListener((Activity) getContext(), this);
        addView(itemView);
    }
 @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getY() < Utils.getScreenHeight() - Utils.dp2px(254f) && isShowing()) {
            dismiss();
        }
        return super.onTouchEvent(event);
    }
private void showSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && mEditText != null) {
            mEditText.post(() -> {
                mEditText.requestFocus();
                inputMethodManager.showSoftInput(mEditText, 0);
            });
            new Handler().postDelayed(() -> {
                changeLayoutNullParams(true);
                changeEmojiPanelParams(0);
            }, 200);
        }
    }


    private void hideSoftKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && mEditText != null) {
            inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }
 private void changeLayoutNullParams(boolean isShowSoftKeyBoard) {
        LinearLayout.LayoutParams params = (LayoutParams) mLayoutNull.getLayoutParams();
        if (isShowSoftKeyBoard) {
            params.weight = 1;
            params.height = 0;
            mLayoutNull.setLayoutParams(params);
        } else {
            params.weight = 0;
            params.height = mDisplayHeight;
            mLayoutNull.setLayoutParams(params);
        }
    }

    private void changeEmojiPanelParams(int keyboardHeight) {
        if (mLayoutEmojiPanel != null) {
            LinearLayout.LayoutParams params = (LayoutParams) mLayoutEmojiPanel.getLayoutParams();
            params.height = keyboardHeight;
            mLayoutEmojiPanel.setLayoutParams(params);
        }
    }

    boolean isVisiableForLast = false;

    public void addOnSoftKeyBoardVisibleListener(Activity activity, final OnKeyBoardStateListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            //计算出可见屏幕的高度
            int displayHight = rect.bottom - rect.top;
            //获得屏幕整体的高度
            int hight = decorView.getHeight();
            //获得键盘高度
            int keyboardHeight = hight - displayHight - Utils.calcStatusBarHeight(getContext());
            boolean visible = (double) displayHight / hight < 0.8;
            if (visible != isVisiableForLast) {
                listener.onSoftKeyBoardState(visible, keyboardHeight, displayHight - Utils.dp2px(48f));
            }
            isVisiableForLast = visible;
        });
    }


    @Override
    public void onSoftKeyBoardState(boolean visible, int keyboardHeight, int displayHeight) {
        this.isKeyBoardShow = visible;
        if (visible) {
            mKeyBoardHeight = keyboardHeight;
            mDisplayHeight = displayHeight;
        }
    }

	}
```

 3. 将自定义的布局加入到主页内容当中，然后我们不用设置 windowSoftInputMode 就可以了。布局：
 
```
   <?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".ui.CustomActivity">

    <include
            android:id="@+id/custom_top_layout"
            layout="@layout/toolbar_layout"/>

    <android.support.v7.widget.RecyclerView
            android:id="@+id/custom_items"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintTop_toBottomOf="@+id/custom_top_layout"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
    >

    </android.support.v7.widget.RecyclerView>

    <com.sasucen.softinput.widget.EmojiPanelView
            android:id="@+id/layout_face_panel"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_constraintBottom_toBottomOf="parent">

    </com.sasucen.softinput.widget.EmojiPanelView>

</android.support.constraint.ConstraintLayout>

```

效果图：


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce6ae2349c209?w=341&h=675&f=gif&s=1494885)

 这个效果还是不错的，但是现在大部分APP都是沉浸式状态栏了，那么我们也加上沉浸式状态栏看看！


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce6be34a62ce7?w=1080&h=1920&f=png&s=435742)

哦豁，输入框被遮罩了！接下来咱们进入第三步——最终填坑！

### 最终填坑
------

我在走到这个地方的时候，当时记得抓瞎。百度了好多都没有提及提及软键盘遮罩和沉浸式状态栏之间的联系，使用windowSoftInputMode 的时候有效果，但是并不理想，因为EditText与软键盘没有间距了，如下图。


![](https://user-gold-cdn.xitu.io/2018/12/21/167ce6f2fcc3768c?w=540&h=960&f=png&s=174083)

后来咨询上面的大佬的时候，他给了我一个思路——状态栏高度的丢失。后来我尝试在屏幕可见高度以及屏幕整体高度的尺寸上做计算，结果都失败了，EditText 完全被遮罩！因为不管 layout 增加还是还是减少状态栏的高度，EditText 的位置始终在软键盘遮罩的位置。本来打算通过设置 titbar 的  padding 和修改状态栏的颜色，实现沉浸式状态栏，但是钻牛角尖的我始终不甘心！后来想起之前看到的一篇文章[随手记技术团队的博客](https://juejin.im/post/5a25f6146fb9a0452405ad5b)介绍“fitsSystemWindows”属性的说明，于是我进行了尝试：

```
  <?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".ui.CustomActivity">

   ······

    <com.sasucen.softinput.widget.EmojiPanelView
            android:id="@+id/layout_face_panel"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true"
            app:layout_constraintBottom_toBottomOf="parent">

    </com.sasucen.softinput.widget.EmojiPanelView>

</android.support.constraint.ConstraintLayout>

```



![](https://user-gold-cdn.xitu.io/2018/12/21/167ce73db2d7cfac?w=540&h=960&f=png&s=189126)

------
以上所述即是我自己关于软键盘的踩坑总结，希望自己在下次不清楚的时候可以回来看看，也希望可以帮助到有需要的人。如有谬误，还请各位指正！



