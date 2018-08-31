## 1.SwipeBackLayout控制属性
- ###### 1. 是否打开滑动手势
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

## 2. 基于SwipeBackLayout的Activity封装
> -  HorizontalSwipeBackActivity 继承后可快速实现右滑返回
> -  VerticalSwipeBackActivity 继承后可快速实现左滑返回，默认不带滑动边缘加深效果


## 3. 项目集成
### Step One:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step Two:
```
dependencies {
	        implementation 'com.github.1004145468:swipeback-android:1.0.1'
	}
```
