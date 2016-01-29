# FrescoPlus
##基于Fresco图片库的二次封装，只需2步就可以让你的应用接入Fresco！
由于Fresco的使用和配置起来需要写一堆代码，没认真看文档和源码根本无法很好的了解Fresco，所以基于Fresco二次封装，配置目前基本保持最优。
#使用
##1、在Application初始化FrescoPlus
```
        FrescoPlusConfig config = FrescoPlusConfig.newBuilder(this)
                .setDebug(true)
                .setTag("Sunzxyong")
                .setBitmapConfig(Bitmap.Config.RGB_565)
                .setDiskCacheDir(this.getCacheDir())
                .setMaxDiskCacheSize(80)
                .build();
        FrescoPlusInitializer.getInstance().init(this,config);
```
##2、在xml中使用FrescoPlusView作为显示图片的View，然后直接使用即可，有五种加载方式，如下
```
    <com.sunzxy.frescopluslib.widget.FrescoPlusView
        android:id="@+id/fresco_view"
        xmlns:fresco="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        fresco:viewAspectRatio="1.5" />
```
###2.1、五种加载图片的方式：
分别提供了精简使用和三种不同的回调，有图片加载成功的回调和下载Bitmap成功并返回的回调。
```
        FrescoPlusView mFrescoPlusView = (FrescoPlusView) findViewById(R.id.fresco_view);
        Uri uri = Uri.parse("http://pic23.nipic.com/20120812/4277683_204018483000_2.jpg");
        //Use
        //——————————————————————————————————————————————————————————————————————————————————————————
        //第一种.默认配置加载图片，默认配置可在配置中心DefaultConfigCentre更改作为全局配置
        mFrescoPlusView.showImage(uri);
        //第二种.默认配置加载图片
        mFrescoPlusView.showImage(R.mipmap.ic_launcher);
        //第三种.加载图片
        FrescoPlusFetcher.newFetcher()
                .withFadeDuration(350)
                .withDefaultDrawable(getResources().getDrawable(R.mipmap.ic_launcher))
                .build()
                .fetch(mFrescoPlusView,uri);
        //第四种.加载图片成功的回调
        FrescoPlusFetcher.newFetcher()
                .withFadeDuration(350)
                .withDefaultDrawable(getResources().getDrawable(R.mipmap.ic_launcher))
                .build()
                .fetch(mFrescoPlusView, uri, new FPFetchCallback<ImageInfo>() {
                    @Override
                    public void onSuccess(ImageInfo result) {
                        // do something
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
        //第五种.不显示图片直接返回Bitmap
        FrescoPlusFetcher.newFetcher()
                .withFadeDuration(350)
                .withRequestPriority(FrescoPriority.HIGH)
                .withAutoRotateEnabled(true)
                .build()
                .fetch(uri, new FPLoadCallback<Bitmap>() {
                    @Override
                    public void onSuccess(Uri uri, Bitmap result) {
                        // do something
                    }

                    @Override
                    public void onFailure(Uri uri, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Uri uri) {

                    }
                });
```
###2.2、关于Fresco更多内容，请看官方文档


#License
>                                 
>Copyright {yyyy} {name of copyright owner}
>   
>  Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
