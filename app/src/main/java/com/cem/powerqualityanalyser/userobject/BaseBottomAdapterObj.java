package com.cem.powerqualityanalyser.userobject;

public class BaseBottomAdapterObj {
    private  String title;//文字
    private  int imgResID;//图片资源ID
    private  int imgResID2;//图片资源ID
    private  boolean more;//是否显示更多>图标
    private  int id; //ID 也就是序号
    private  DataType type;//数据类型
    private  int switchindex=1;//点击菜单操作，如果是Switch 类型，则是0/1 如果是 Zoom类型则是当前放大值
    private  String[] moreArray;//更多菜单选项

    private String onStr;//Switch 类型开字符
    private String offStr;//Switch 类型关字符
    private int maxZoom;//最大放大值
    private boolean firstButton;
    private boolean isChangeText = true;
    private boolean hideTitle;

    public BaseBottomAdapterObj(int id,String title){
        type= DataType.Text;
        this.title=title;
        this.id = id;
    }

    public BaseBottomAdapterObj(int id,boolean hideTitle,String title){
        type= DataType.ImageZoom;
        this.title=title;
        this.id = id;
        this.hideTitle = hideTitle;
    }

    public BaseBottomAdapterObj(String title){
        type= DataType.Text;
        this.title=title;
    }

    public BaseBottomAdapterObj(int id,String title , int imgResID){
        type= DataType.TextImage;
        this.id = id;
        this.title=title;
        this.imgResID=imgResID;
    }

    public BaseBottomAdapterObj(String title , int imgResID){
        type= DataType.TextImage;
        this.title=title;
        this.imgResID=imgResID;
    }
    public BaseBottomAdapterObj(String title, String[] morearr){
        moreArray=morearr;
        init(title,0,true);
    }

    public BaseBottomAdapterObj(int id,String title, String[] morearr){
        moreArray=morearr;
        this.id = id;
        init(title,0,true);
    }

    /**
     * 新增 可zoom 的Pop
     * @param id
     * @param title
     * @param imgResID
     * @param morearr
     */
    public BaseBottomAdapterObj(int id,String title, int imgResID,String[] morearr){
        moreArray=morearr;
        this.id = id;
        init(title,imgResID,true);
        type= DataType.TextZoom;
    }

    /**
     * 瞬变 Pop Index  为F的情况处理
     */
    private boolean isFL1;
    public BaseBottomAdapterObj(int id,String title, int imgResID,String[] morearr,boolean fL1){
        moreArray=morearr;
        this.id = id;
        init(title,imgResID,true);
        type= DataType.TextZoom;
        this.isFL1 = fL1;
    }

    public boolean isFL1(){
        return isFL1;
    }


    public BaseBottomAdapterObj(int id,String title, String[] morearr, boolean isChangeText){
        this(title,morearr);
        this.isChangeText = isChangeText;
    }

    public BaseBottomAdapterObj(String title, String[] morearr, boolean isChangeText){
        this(title,morearr);
        this.id = id;
        this.isChangeText = isChangeText;
    }

    public boolean isChangeText() {
        return isChangeText;
    }

    public BaseBottomAdapterObj(String title, int resid, boolean more){
        init(title,resid,more);
    }

    public BaseBottomAdapterObj(int id,String title, String onStr, String offStr){
        type= DataType.Switch;
        this.id = id;
        this.title=title;
        this.onStr=onStr;
        this.offStr=offStr;
    }


    public BaseBottomAdapterObj(String title, String onStr, String offStr){
        type= DataType.Switch;
        this.title=title;
        this.onStr=onStr;
        this.offStr=offStr;
    }

    public BaseBottomAdapterObj(int id,String title, int imgResID , int zoomMax){
        type= DataType.Zoom;
        this.id = id;
        this.title=title;
        this.imgResID=imgResID;
        this.maxZoom=zoomMax;
    }

    public BaseBottomAdapterObj(String title, int imgResID , int zoomMax){
        type= DataType.Zoom;
        this.title=title;
        this.imgResID=imgResID;
        this.maxZoom=zoomMax;
    }
    public BaseBottomAdapterObj(int id,int imgResID, int imgResID2 ){
        type= DataType.Switch;
        this.id = id;
        this.imgResID=imgResID;
        this.imgResID2=imgResID2;
    }

    public BaseBottomAdapterObj(int id,String title,boolean hideTitle,int imgResID2){
        type= DataType.ImageZoom;
        this.id = id;
        this.hideTitle = hideTitle;
        this.title = title;
        this.imgResID2=imgResID2;
    }

    public BaseBottomAdapterObj(int imgResID, int imgResID2 ){
        type= DataType.Switch;
        this.imgResID=imgResID;
        this.imgResID2=imgResID2;
    }

    private void init(String title, int resid, boolean more) {
        this.title = title;
        this.imgResID = resid;
        this.more = more;
        if (more) {
            this.title = title;//图标之间增加间距
        }
        if (title == null || title.equals("")) {
            if (resid != 0) {
                type = DataType.Image;
            }

        } else {
            if (resid != 0) {
                type = DataType.TextImage;
            } else {
                type = DataType.Text;
            }
            if (title.contains("\n") && !title.contains("x")) {
                type = DataType.Switch;
            }
        }

    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public void setSwitchindex(int switchindex) {
        this.switchindex = switchindex;
    }

    public String[] getMoreArray() {
        return moreArray;
    }

    public int getSwitchindex() {
        return switchindex;
    }

    public DataType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getImgResID() {
        return imgResID;
    }

    public int getImgResID2() {
        return imgResID2;
    }

    public String getOnStr() {
        return onStr;
    }

    public String getOffStr() {
        return offStr;
    }

    public boolean isMore() {
        return more;
    }

    public boolean isHideTitle(){
        return hideTitle;
    }
    public  enum  DataType{
        Text,
        Switch,
        Image,
        TextImage,
        Zoom,
        ImageZoom,
        TextZoom
    }

    public boolean isFirstButton() {
        return firstButton;
    }

    public void setFirstButton(boolean firstButton) {
        this.firstButton = firstButton;
    }
}
