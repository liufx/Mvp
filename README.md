# Mvp
mvp 简单使用
实现步骤

**BaseView**
```Java
public interface BaseView {

}
```
接口View的基类，仅定义了最基本的方法，具体页面的view继承的接口，添加属于自己页面的方法。

**Contract 契约类**

这是Google MVP与其他实现方式的不同之一，契约类用于定义同一个界面的view和presenter的接口，通过规范的方法命名或注释，可以清晰的看到整个页面的逻辑。

```Java
public interface MainContract {

    interface View extends BaseView {

        void onTouTiaoSuccess(List<TouTiao> data);

        void onTouTiaoError(ResponseException e);

    }

    interface Presenter {
        void getTouTiaoData(String type);

    }
}
```

**BasePresenter**

为了网址Rxjava内存泄露需要在页面关闭是取消订阅
```Java
public abstract class BasePresenter<V extends BaseView> {
    protected V view;
    protected Context context;
    private CompositeDisposable compositeDisposable;

    public BasePresenter(V view) {
        if (view instanceof BaseFragment) {
            this.context = ((BaseFragment) view).context;
        } else {
            this.context = (Context) view;
        }
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * 保存RxJava绑定关系
     */
    public void addDisposable(Disposable disposable) {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.add(disposable);
        }
    }

    /**
     * 取消单个RxJava绑定
     */
    public void removeDisposable(Disposable disposable) {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.remove(disposable);
        }
    }

    /**
     * 取消当前Presenter的全部RxJava绑定，置空view
     */
    public void detach() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        view = null;
    }
}
```
**具体的Impl类**

```Java
public class MainPresenterImpl extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenterImpl(MainContract.View view) {
        super(view);
    }

    @Override
    public void getTouTiaoData(String type) {
        RequestManager.getInstance().execute(this, RetrofitManager.getInstance().create(YoulingApis.class).touTiao(type),
                new LoadingObserver<List<TouTiao>>(context, true, true) {
                    @Override
                    protected void onSuccess(List<TouTiao> data) {

                        view.onTouTiaoSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {

                        view.onTouTiaoError(e);
                    }
                });
    }
}
```
**BaseActivity类**
```Java
public abstract class BaseActivity extends AppCompatActivity {
    protected Context context;
    private Unbinder unbinder;

    // 初始化布局文件id
    protected abstract @LayoutRes
    int initLayoutResID();

    // 初始化数据
    protected abstract void initData();

    // 初始化控件
    protected abstract void initView();

    // 页面初始化数据请求、内容加载
    protected abstract void loadData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutResID());
        context = this;
        unbinder = ButterKnife.bind(this);

        initData();
        initView();
        loadData();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
```
**BaseMvpActivity**
```Java
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P presenter;

    // 初始化Presenter
    protected abstract P initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();
        }
        super.onDestroy();
    }

}
```
**具体在MainActivity实现**
```Java
public class MainActivity extends BaseMvpActivity<MainPresenterImpl> implements MainContract.View {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    List<TouTiao> data;
    TouTiaoAdapter touTiaoAdapter;

    @Override
    protected MainPresenterImpl initPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    protected int initLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        data = new ArrayList<>();
    }

    @Override
    protected void initView() {
        touTiaoAdapter = new TouTiaoAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(touTiaoAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(MainActivity.this).resumeRequests();//恢复Glide加载图片
                } else {
                    Glide.with(MainActivity.this).pauseRequests();//禁止Glide加载图片
                }
            }
        });
        touTiaoAdapter.setItemOnClickListener(new TouTiaoAdapter.ItemOnClickListener() {
            @Override
            public void onClick(int pos) {

            }
        });

    }

    @Override
    protected void loadData() {

        presenter.getTouTiaoData("0");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onTouTiaoSuccess(List<TouTiao> data) {
        this.data.addAll(data);
        touTiaoAdapter.notifyDataSetChanged();

    }

    @Override
    public void onTouTiaoError(ResponseException e) {

    }


}
```
**接口说明**

列表接口采用的是幽灵API http://tt.ylapi.cn 头条数据接口

**感谢**
[SheHuan的简书blog](https://www.jianshu.com/p/f9af26c2116c?utm_source=oschina-app)   
