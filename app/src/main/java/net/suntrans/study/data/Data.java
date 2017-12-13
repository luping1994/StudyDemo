package net.suntrans.study.data;

import java.util.List;

/**
 * Created by Looney on 2017/12/13.
 * Des:
 */

public class Data {

    /**
     * result : true
     * container : {"title":"测试","bgColor":"ffffff","height":1080,"width":1920,"bgImage":"http://tit.suntrans-cloud.com//app/building/floor_1.png"}
     * elements : [{"type":"image","height":50,"radius":0,"id":1,"house_id":1,"channel_id":45,"x":"30","y":"204","length":"66","width":"66","title":"肉菜加工间 照明灯3","status":true,"openUrl":"http://tit.suntrans-cloud.com//app/building/light_4_on.png","closeUrl":"http://tit.suntrans-cloud.com//app/building/light_4_off.png"}]
     */

    public boolean result;
    public ContainerBean container;
    public List<ElementsBean> elements;

    public static class ContainerBean {
        /**
         * title : 测试
         * bgColor : ffffff
         * height : 1080
         * width : 1920
         * bgImage : http://tit.suntrans-cloud.com//app/building/floor_1.png
         */

        public String title;
        public String bgColor;
        public int height;
        public int width;
        public String bgImage;
    }

    public static class ElementsBean {
        /**
         * type : image
         * height : 50
         * radius : 0
         * id : 1
         * house_id : 1
         * channel_id : 45
         * x : 30
         * y : 204
         * length : 66
         * width : 66
         * title : 肉菜加工间 照明灯3
         * status : true
         * openUrl : http://tit.suntrans-cloud.com//app/building/light_4_on.png
         * closeUrl : http://tit.suntrans-cloud.com//app/building/light_4_off.png
         */

        public String type;
        public int height;
        public int radius;
        public int id;
        public int house_id;
        public int channel_id;
        public int x;
        public int y;
        public int width;
        public String title;
        public boolean status;
        public String openUrl;
        public String closeUrl;
    }
}
