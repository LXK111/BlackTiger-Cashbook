package com.example.ui;

public class bill {
    private String first_type,second_type;
    private String money_cost;
    //private       time;  //时间

    public bill(String first_type,String second_type,String money_cost){
        this.first_type = first_type;
        this.second_type = second_type;
        this.money_cost = money_cost;
    }


    public String getFirst_type(){
        return first_type;
    }
    public String getSecond_type(){
        return second_type;
    }
    public String getMoney_cost(){
        return money_cost;
    }

}
