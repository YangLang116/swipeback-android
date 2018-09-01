## 1. 知识背景
 项目中常用的侧滑返回控件大部分都是通过内部封装ViewDragHelper进行View拖拽实现的，而采用ViewDragHelper的方式必然会带来另外两个问题：

- Q1：什么样的右滑才是退出界面，而不是滑动界面内容？
- A1：ViewDragHelper传入参数View作为ControllerView，通过改变它的布局参数实现拖拽效果。为了达到Activity右滑返回的能力，那么ControllerView可以是PhoneWindow下DecorView的子View，即ContentView，并将Activity的背景为透明，当ContentView被移出屏幕时，finish当前的Activity，这样就实现了全屏右滑返回。理想的情况下一切都正常，但是当界面中存在滑动控件时，那么棘手的滑动冲突的问题就出现。作为基本库的封装，为了尽量的避免这一问题，就需要将右滑触发ContentView移动的条件设置得更为苛刻，恰恰ViewDragHelper为建立这一苛刻条件提供了帮助，通过ViewDragHelper设置setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)，即只在View的边缘(可调整宽度)开始滑动才认为是拖拽。总之，通过ViewDragHelper实现的侧滑返回有一个现象就是只能在边缘触发。

- Q2:  当Activity边缘恰好有能右滑的控件，右滑时却无法滑动该控件，而是导致应用退出，那么滑动冲突问题真的没解了么？
- A2:  No~， 解决的办法还是有的，在该自定义控件中创建setEnableGesture(boolean enable)方法，通过控制变量的方式来决定是否在ViewDragHelper消费触摸事件之前对触摸事件进行拦截。上层业务代码需要调用setEnableGesture(boolean)来打开和禁用Activity侧滑的能力，来避免滑动冲突。这种方案虽然是能解决问题，但是这样的库封装就存在不合理性，因为上层业务需要关心底层实现。

## 2. SwipeBackLayout的优势
- ###### 细节处理更优雅，支持全屏滑动返回，并在底层处理滑动冲突，做到让上层业务对底层实现细节无感知。
- ###### 封装完善，SwipeBackLayout提供丰富的控制接口，如果想快速实现界面滑动返回，可直接继承（HorizontalSwipeBackActivity、VerticalSwipeBackActivity)
- ###### 采用智能决策以及手动决策相结合，smartSmoothScroll()能根据当前的滑动所在位置，决策出平滑滑动到起始点还是结束位置；smoothToEnd() 会忽略当前的滑动所在位置，直接平滑到结束位置。

## 3. SwipeBackLayout属性控制
- ###### 1. 是否强制禁用滑动手势
> setSwipeGestureEnable(boolean enable)

- ###### 2. 设置滑动返回方向
> setSwipeOrientation(int orientation)

- ###### 3. 设置滑动返回敏感度
> setSwipeSensitivity(float sensitivity)

- ###### 4. 设置滑动背景渐变色
> setSwipeScrimColor(int color)

- ###### 5. 设置滑动返回速度
> setSwipeSpeed(int duration)

- ###### 6. 滑动边缘是否需要颜色加深效果
> needSwipeShadow(boolean needShadow)

- ###### 7. 自动滑动到结尾
> smoothToEnd()

- ###### 8. 智能滑动（根据当前的偏移量决定滑动到起始还是结尾）
> smartSmoothScroll() 

- ###### 9. 滑动监听
> -  addOnSwipeProgressChangedListener(OnSwipeProgressChangedListener listener)
> - removeOnSwipeProgressChangedListener(OnSwipeProgressChangedListener listener)

## 4. 基于SwipeBackLayout的Activity封装
> -  HorizontalSwipeBackActivity 继承后可快速实现右滑返回
> -  VerticalSwipeBackActivity 继承后可快速实现左滑返回，默认不带滑动边缘加深效果
>   通过getSwipeLayout() 可获取SwipeBackLayout的实例


## 5. 项目集成
###### Step One:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

###### Step Two:
```
dependencies {
	        implementation 'com.github.1004145468:swipeback-android:1.0.1'
	}
```

## 4. 测试Demo
[下载地址](https://github.com/1004145468/swipeback-android/raw/master/app-debug.apk)
