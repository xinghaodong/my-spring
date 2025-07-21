    package com.example.myspring.service;

    import com.example.myspring.entity.Cesium;

    import java.util.List;
    import java.util.Map;

    public interface CesiumService {
        List<Cesium> getAll(); // 改成返回 DTO

        Cesium getById(Integer id);


       Map<String,Object> getCesiumPage(int page, int pageSize);

        /**
         * 修改航线信息
         *
         * @param cesium
         */
        Cesium updateCesium(Cesium cesium) ;

        /**
         * 删除航线信息
         *
         * @param id
         */
        void deleteCesium(Integer id);

    }