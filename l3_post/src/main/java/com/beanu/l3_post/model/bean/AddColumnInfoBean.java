package com.beanu.l3_post.model.bean;

import java.util.List;

/**
 * @author lizhi
 * @date 2017/11/13.
 */

public class AddColumnInfoBean {

    /**
     * fid : 158
     * name : 济宁论坛
     * type_require : false
     * types : [{"name":"其他","id":182},{"name":"原创文学","id":183},{"name":"民生聚焦","id":184},{"name":"摄影沙龙","id":185},{"name":"骑行天地","id":186},{"name":"婚恋交友","id":187},{"name":"回声","id":1488}]
     * classified_require : null
     * classifieds : []
     */

    private String fid;
    private String name;
    private Boolean type_require;
    private Boolean classified_require;
    private List<TypesBean> types;
    private List<TypesBean> classifieds;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isType_require() {
        return type_require != null && type_require;
    }

    public void setType_require(boolean type_require) {
        this.type_require = type_require;
    }

    public boolean getClassified_require() {
        return classified_require != null && classified_require;
    }

    public void setClassified_require(Boolean classified_require) {
        this.classified_require = classified_require;
    }

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public List<TypesBean> getClassifieds() {
        return classifieds;
    }

    public void setClassifieds(List<TypesBean> classifieds) {
        this.classifieds = classifieds;
    }

    public static class TypesBean {
        /**
         * name : 其他
         * id : 182
         */

        private String name;
        private int id;

        public boolean isChecked;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
