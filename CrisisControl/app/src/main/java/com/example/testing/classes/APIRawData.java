package com.example.testing.classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIRawData {

    @SerializedName("warnings")
    List<String> warnings;

    @SerializedName("time")
    String time;

    @SerializedName("took")
    String took;

    @SerializedName("totalCount")
    String totalCount;

    @SerializedName("count")
    String count;

    @SerializedName("data")
    List<APIdata> data;

    public APIRawData(List<String> warnings, String time, String took, String totalCount, String count, List<APIdata> data) {
        this.warnings = warnings;
        this.time = time;
        this.took = took;
        this.totalCount = totalCount;
        this.count = count;
        this.data = data;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTook() {
        return took;
    }

    public void setTook(String took) {
        this.took = took;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<APIdata> getData() {
        return data;
    }

    public void setData(List<APIdata> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Model{" +
                "warnings=" + warnings +
                ", time='" + time + '\'' +
                ", took='" + took + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", count='" + count + '\'' +
                ", data=" + data +
                '}';
    }

    public class APIdata {
        @SerializedName("id")
        String id;

        @SerializedName("score")
        String score;

        @SerializedName("fields")
        Fields fields;

        public APIdata(String id, String score, Fields fields) {
            this.id = id;
            this.score = score;
            this.fields = fields;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public Fields getFields() {
            return fields;
        }

        public void setFields(Fields fields) {
            this.fields = fields;
        }

        @Override
        public String toString() {
            return "APIdata{" +
                    "id='" + id + '\'' +
                    ", score='" + score + '\'' +
                    ", fields=" + fields +
                    '}';
        }

        public class Fields{
            @SerializedName("date")
            _Date date;

            @SerializedName("country")
            List<Country> country;

            @SerializedName("primary_country")
            PrimaryCountry primary_country;

            @SerializedName("source")
            List<_Source> source;

            @SerializedName("title")
            String title;

            @SerializedName("url")
            String url;

            @SerializedName("status")
            String status;

            public Fields(_Date date, List<Country> country, PrimaryCountry primary_country, List<_Source> source, String title, String url, String status) {
                this.date = date;
                this.country = country;
                this.primary_country = primary_country;
                this.source = source;
                this.title = title;
                this.url = url;
                this.status = status;
            }

            public _Date getDate() {
                return date;
            }

            public void setDate(_Date date) {
                this.date = date;
            }

            public List<Country> getCountry() {
                return country;
            }

            public void setCountry(List<Country> country) {
                this.country = country;
            }

            public PrimaryCountry getPrimary_country() {
                return primary_country;
            }

            public void setPrimary_country(PrimaryCountry primary_country) {
                this.primary_country = primary_country;
            }

            public List<_Source> getSource() {
                return source;
            }

            public void setSource(List<_Source> source) {
                this.source = source;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            @Override
            public String toString() {
                return "Fields{" +
                        "date=" + date +
                        ", country=" + country +
                        ", primary_country=" + primary_country +
                        ", source=" + source +
                        ", title='" + title + '\'' +
                        ", url='" + url + '\'' +
                        ", status='" + status + '\'' +
                        '}';
            }

            public class _Date{
                @SerializedName("date")
                String date;

                public _Date(String date) {
                    this.date = date;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                @Override
                public String toString() {
                    return "_Date{" +
                            "date='" + date + '\'' +
                            '}';
                }
            }

            public class Country {
                @SerializedName("country")
                String country;

                public Country(String country) {
                    this.country = country;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                @Override
                public String toString() {
                    return "Country{" +
                            "country='" + country + '\'' +
                            '}';
                }
            }

            public class PrimaryCountry{
                @SerializedName("primary_country")
                String primary_country;

                public PrimaryCountry(String primary_country) {
                    this.primary_country = primary_country;
                }

                public String getPrimary_country() {
                    return primary_country;
                }

                public void setPrimary_country(String primary_country) {
                    this.primary_country = primary_country;
                }

                @Override
                public String toString() {
                    return "PrimaryCountry{" +
                            "primary_country='" + primary_country + '\'' +
                            '}';
                }
            }

            public class _Source {
                @SerializedName("name")
                String name;
                @SerializedName("homepage")
                String homepage;

                public _Source(String name, String homepage) {
                    this.name = name;
                    this.homepage = homepage;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getHomepage() {
                    return homepage;
                }

                public void setHomepage(String homepage) {
                    this.homepage = homepage;
                }

                @Override
                public String toString() {
                    return "_Source{" +
                            "name='" + name + '\'' +
                            ", homepage='" + homepage + '\'' +
                            '}';
                }
            }
        }

    }


}
