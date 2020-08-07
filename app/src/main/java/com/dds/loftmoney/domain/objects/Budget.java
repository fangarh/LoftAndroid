package com.dds.loftmoney.domain.objects;


    public class Budget implements Comparable< Budget >{
        //region ctor...

        public Budget() {

        }

        public Budget(String id, String name, String price, String date) {
            this.name = name;
            this.price = price;
            this.id = id;
            this.date = date;
        }

        //endregion

        //region private members declaration


        private String name;

        private String price;

        private String date;

        private String id;

        //endregion

        //region getters/setters

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) { this.id = id; }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDate() { return date; }

        public void setDate(String date) { this.date = date; }

        // endregion


        //region Comparable

        @Override
        public int compareTo(com.dds.loftmoney.domain.objects.Budget budget) {
            if(budget == null || this.date == null) return -1;
            return this.date.compareTo(budget.date);
        }


        //endregion
    }