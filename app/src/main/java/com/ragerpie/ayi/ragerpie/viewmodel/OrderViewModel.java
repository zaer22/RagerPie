package com.ragerpie.ayi.ragerpie.viewmodel;

import android.view.View;

import com.ragerpie.ayi.ragerpie.model.beans.GoodsBean;
import com.ragerpie.ayi.ragerpie.model.beans.OrderBean;
import com.ragerpie.ayi.ragerpie.model.beans.ResponseWrapper;
import com.ragerpie.ayi.ragerpie.model.impls.OrderModel;
import com.ragerpie.ayi.ragerpie.model.interfaces.IOrderModel;
import com.ragerpie.ayi.ragerpie.net.RagerSubscriber;
import com.ragerpie.ayi.ragerpie.util.LogUtils;
import com.ragerpie.ayi.ragerpie.view.adapter.OrderListAdapter;

import java.util.List;

import retrofit2.Response;

/**
 * Created by WangBo on 2016/10/28.
 */

public class OrderViewModel {
    private IOrderModel orderModel;

    /**
     * xml用到的字段
     */
    private String realName;
    private String phone;
    private String wechatId;
    private String time;
    private String address;
    private String sendMessage;
    private String remarks;
    private Float totalPrice;
    private String message;
    private int status;
    private List<GoodsBean> goodList;


    private int indexOfDataList;
    private List<OrderBean> dataList;
    private OrderListAdapter adapter;



    public OrderViewModel(int indexOfDataList, OrderListAdapter adapter, List<OrderBean> dataList) {
        this.indexOfDataList = indexOfDataList;
        this.adapter = adapter;
        this.dataList = dataList;
        orderModel = new OrderModel();
    }

    public void fillData(String realName, String phone, String wechatId, String time,
                         String address, String sendMessage, String remarks,
                         Float totalPrice,
                         int status, List<GoodsBean> goodList) {
        this.realName = realName;
        this.phone = phone;
        this.wechatId = wechatId;
        this.time = time;
        this.address = address;
        this.sendMessage = sendMessage;
        this.remarks = remarks;
        this.goodList = goodList;
        this.totalPrice = totalPrice;
        this.status = status;
        message = "￥" + totalPrice + " | " + remarks;
    }

    public String getRealName() {
        return realName;
    }

    public String getPhone() {
        return phone;
    }

    public String getWechatId() {
        return wechatId;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getSendMessage() {
        return sendMessage;
    }

    public String getRemarks() {
        return remarks;
    }

    public List<GoodsBean> getGoodList() {
        return goodList;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhoneStr() {
        return "电话：" + phone;
    }

    public String getWeichatStr() {
        return "微信：" + realName;
    }

    public String getAddrStr() {
        return "地址：" + address;
    }

    public String getSendMessageStr() {
        return "时间：" + sendMessage;
    }

    public String getRemarkStr() {
        return "备注：" + remarks;
    }

    public String getTotalPriceStr() {
        return totalPrice + "元";
    }

    public void onContainerClick(View view) {
        int lastExpandIndex = adapter.getLastExpandIndex();
        boolean isClickedItemExpanded = dataList.get(indexOfDataList).isExpand();
        if (isClickedItemExpanded) {
            //点的是打开的，关闭
            dataList.get(indexOfDataList).setExpand(false);
            adapter.setLastExpandIndex(-1);
        } else {
            //关闭上一个打开的
            if (lastExpandIndex != -1) {
                dataList.get(lastExpandIndex).setExpand(false);
                adapter.notifyItemChanged(lastExpandIndex);
                LogUtils.d("关闭上一个打开的:" + lastExpandIndex);
            }
            //打开当前点击的
            dataList.get(indexOfDataList).setExpand(true);
            adapter.setLastExpandIndex(indexOfDataList);
        }
        adapter.notifyItemChanged(indexOfDataList);

    }

    public void onInvalidOrder(View view) {
        this.status = OrderBean.STATE_NOUSED;
        orderModel.invalidOrder(String.valueOf(dataList.get(indexOfDataList).getId()),
                new RagerSubscriber<Response<ResponseWrapper>>() {

                });
    }

    public void onFinishOrder(View view) {
        this.status = OrderBean.STATE_DEAL;
        orderModel.invalidOrder(String.valueOf(dataList.get(indexOfDataList).getId()),
                new RagerSubscriber<Response<ResponseWrapper>>() {

                });
    }

}
