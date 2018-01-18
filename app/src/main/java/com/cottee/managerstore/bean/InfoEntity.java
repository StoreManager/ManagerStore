package com.cottee.managerstore.bean;

/**
 * Created by chn on 2018/1/18.
 * (╯°□°）╯︵ ┻━┻ MMP好气啊！
 * (╯‵□′)╯︵┻━┻ 老子怒掀桌子！
 * ┻━┻︵╰(‵□′)╯︵┻━┻老子双手掀桌！
 * ┬─┬﻿ ノ( ゜-゜ノ) 算了，我不生气了！日子还得过老老实实敲吧~
 */

public class InfoEntity {

    /**
     * status : 0
     * message : query ok
     * request_id : 42e2550a-fc09-11e7-b52b-84ad58e19cc9
     * result : {"location":{"lat":40.129436,"lng":116.421021},"address":"北京市昌平区东北路","formatted_addresses":{"recommend":"昌平区北七家北亚花园(东北路南)","rough":"昌平区北七家北亚花园(东北路南)"},"address_component":{"nation":"中国","province":"北京市","city":"北京市","district":"昌平区","street":"东北路","street_number":"东北路"},"ad_info":{"nation_code":"156","adcode":"110114","city_code":"156110000","name":"中国,北京市,北京市,昌平区","location":{"lat":40.129436,"lng":116.421021},"nation":"中国","province":"北京市","city":"北京市","district":"昌平区"},"address_reference":{"street_number":{"title":"","location":{"lat":40.130589,"lng":116.420586},"_distance":125.6,"_dir_desc":"南"},"business_area":{"title":"北七家","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"},"famous_area":{"title":"北七家","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"},"town":{"title":"北七家镇","location":{"lat":40.129436,"lng":116.421021},"_distance":0,"_dir_desc":"内"},"street":{"title":"东北路","location":{"lat":40.130589,"lng":116.420586},"_distance":125.6,"_dir_desc":"南"},"landmark_l2":{"title":"北亚花园","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"}}}
     */

    private int status;
    private String message;
    private String request_id;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * location : {"lat":40.129436,"lng":116.421021}
         * address : 北京市昌平区东北路
         * formatted_addresses : {"recommend":"昌平区北七家北亚花园(东北路南)","rough":"昌平区北七家北亚花园(东北路南)"}
         * address_component : {"nation":"中国","province":"北京市","city":"北京市","district":"昌平区","street":"东北路","street_number":"东北路"}
         * ad_info : {"nation_code":"156","adcode":"110114","city_code":"156110000","name":"中国,北京市,北京市,昌平区","location":{"lat":40.129436,"lng":116.421021},"nation":"中国","province":"北京市","city":"北京市","district":"昌平区"}
         * address_reference : {"street_number":{"title":"","location":{"lat":40.130589,"lng":116.420586},"_distance":125.6,"_dir_desc":"南"},"business_area":{"title":"北七家","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"},"famous_area":{"title":"北七家","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"},"town":{"title":"北七家镇","location":{"lat":40.129436,"lng":116.421021},"_distance":0,"_dir_desc":"内"},"street":{"title":"东北路","location":{"lat":40.130589,"lng":116.420586},"_distance":125.6,"_dir_desc":"南"},"landmark_l2":{"title":"北亚花园","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"}}
         */

        private LocationBean location;
        private String address;
        private FormattedAddressesBean formatted_addresses;
        private AddressComponentBean address_component;
        private AdInfoBean ad_info;
        private AddressReferenceBean address_reference;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public FormattedAddressesBean getFormatted_addresses() {
            return formatted_addresses;
        }

        public void setFormatted_addresses(FormattedAddressesBean formatted_addresses) {
            this.formatted_addresses = formatted_addresses;
        }

        public AddressComponentBean getAddress_component() {
            return address_component;
        }

        public void setAddress_component(AddressComponentBean address_component) {
            this.address_component = address_component;
        }

        public AdInfoBean getAd_info() {
            return ad_info;
        }

        public void setAd_info(AdInfoBean ad_info) {
            this.ad_info = ad_info;
        }

        public AddressReferenceBean getAddress_reference() {
            return address_reference;
        }

        public void setAddress_reference(AddressReferenceBean address_reference) {
            this.address_reference = address_reference;
        }

        public static class LocationBean {
            /**
             * lat : 40.129436
             * lng : 116.421021
             */

            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        public static class FormattedAddressesBean {
            /**
             * recommend : 昌平区北七家北亚花园(东北路南)
             * rough : 昌平区北七家北亚花园(东北路南)
             */

            private String recommend;
            private String rough;

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public String getRough() {
                return rough;
            }

            public void setRough(String rough) {
                this.rough = rough;
            }
        }

        public static class AddressComponentBean {
            /**
             * nation : 中国
             * province : 北京市
             * city : 北京市
             * district : 昌平区
             * street : 东北路
             * street_number : 东北路
             */

            private String nation;
            private String province;
            private String city;
            private String district;
            private String street;
            private String street_number;

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }
        }

        public static class AdInfoBean {
            /**
             * nation_code : 156
             * adcode : 110114
             * city_code : 156110000
             * name : 中国,北京市,北京市,昌平区
             * location : {"lat":40.129436,"lng":116.421021}
             * nation : 中国
             * province : 北京市
             * city : 北京市
             * district : 昌平区
             */

            private String nation_code;
            private String adcode;
            private String city_code;
            private String name;
            private LocationBeanX location;
            private String nation;
            private String province;
            private String city;
            private String district;

            public String getNation_code() {
                return nation_code;
            }

            public void setNation_code(String nation_code) {
                this.nation_code = nation_code;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getCity_code() {
                return city_code;
            }

            public void setCity_code(String city_code) {
                this.city_code = city_code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public LocationBeanX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanX location) {
                this.location = location;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public static class LocationBeanX {
                /**
                 * lat : 40.129436
                 * lng : 116.421021
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }

        public static class AddressReferenceBean {
            /**
             * street_number : {"title":"","location":{"lat":40.130589,"lng":116.420586},"_distance":125.6,"_dir_desc":"南"}
             * business_area : {"title":"北七家","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"}
             * famous_area : {"title":"北七家","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"}
             * town : {"title":"北七家镇","location":{"lat":40.129436,"lng":116.421021},"_distance":0,"_dir_desc":"内"}
             * street : {"title":"东北路","location":{"lat":40.130589,"lng":116.420586},"_distance":125.6,"_dir_desc":"南"}
             * landmark_l2 : {"title":"北亚花园","location":{"lat":40.129379,"lng":116.421494},"_distance":0,"_dir_desc":"内"}
             */

            private StreetNumberBean street_number;
            private BusinessAreaBean business_area;
            private FamousAreaBean famous_area;
            private TownBean town;
            private StreetBean street;
            private LandmarkL2Bean landmark_l2;

            public StreetNumberBean getStreet_number() {
                return street_number;
            }

            public void setStreet_number(StreetNumberBean street_number) {
                this.street_number = street_number;
            }

            public BusinessAreaBean getBusiness_area() {
                return business_area;
            }

            public void setBusiness_area(BusinessAreaBean business_area) {
                this.business_area = business_area;
            }

            public FamousAreaBean getFamous_area() {
                return famous_area;
            }

            public void setFamous_area(FamousAreaBean famous_area) {
                this.famous_area = famous_area;
            }

            public TownBean getTown() {
                return town;
            }

            public void setTown(TownBean town) {
                this.town = town;
            }

            public StreetBean getStreet() {
                return street;
            }

            public void setStreet(StreetBean street) {
                this.street = street;
            }

            public LandmarkL2Bean getLandmark_l2() {
                return landmark_l2;
            }

            public void setLandmark_l2(LandmarkL2Bean landmark_l2) {
                this.landmark_l2 = landmark_l2;
            }

            public static class StreetNumberBean {
                /**
                 * title :
                 * location : {"lat":40.130589,"lng":116.420586}
                 * _distance : 125.6
                 * _dir_desc : 南
                 */

                private String title;
                private LocationBeanXX location;
                private double _distance;
                private String _dir_desc;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXX location) {
                    this.location = location;
                }

                public double get_distance() {
                    return _distance;
                }

                public void set_distance(double _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXX {
                    /**
                     * lat : 40.130589
                     * lng : 116.420586
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class BusinessAreaBean {
                /**
                 * title : 北七家
                 * location : {"lat":40.129379,"lng":116.421494}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String title;
                private LocationBeanXXX location;
                private int _distance;
                private String _dir_desc;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXX {
                    /**
                     * lat : 40.129379
                     * lng : 116.421494
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class FamousAreaBean {
                /**
                 * title : 北七家
                 * location : {"lat":40.129379,"lng":116.421494}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String title;
                private LocationBeanXXXX location;
                private int _distance;
                private String _dir_desc;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXX {
                    /**
                     * lat : 40.129379
                     * lng : 116.421494
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class TownBean {
                /**
                 * title : 北七家镇
                 * location : {"lat":40.129436,"lng":116.421021}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String title;
                private LocationBeanXXXXX location;
                private int _distance;
                private String _dir_desc;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXXX {
                    /**
                     * lat : 40.129436
                     * lng : 116.421021
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class StreetBean {
                /**
                 * title : 东北路
                 * location : {"lat":40.130589,"lng":116.420586}
                 * _distance : 125.6
                 * _dir_desc : 南
                 */

                private String title;
                private LocationBeanXXXXXX location;
                private double _distance;
                private String _dir_desc;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXX location) {
                    this.location = location;
                }

                public double get_distance() {
                    return _distance;
                }

                public void set_distance(double _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXXXX {
                    /**
                     * lat : 40.130589
                     * lng : 116.420586
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class LandmarkL2Bean {
                /**
                 * title : 北亚花园
                 * location : {"lat":40.129379,"lng":116.421494}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String title;
                private LocationBeanXXXXXXX location;
                private int _distance;
                private String _dir_desc;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public LocationBeanXXXXXXX getLocation() {
                    return location;
                }

                public void setLocation(LocationBeanXXXXXXX location) {
                    this.location = location;
                }

                public int get_distance() {
                    return _distance;
                }

                public void set_distance(int _distance) {
                    this._distance = _distance;
                }

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

                public static class LocationBeanXXXXXXX {
                    /**
                     * lat : 40.129379
                     * lng : 116.421494
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }
    }
}
