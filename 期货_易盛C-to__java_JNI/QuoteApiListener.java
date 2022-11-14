package com.future.api.es.external.quote.listener;

import com.future.api.es.external.common.bean.TapAPIContract;
import com.future.api.es.external.common.listener.ApiListener;
import com.future.api.es.external.quote.bean.TapAPIQuotLoginRspInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteCommodityInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteContractInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteWhole;

public interface QuoteApiListener extends ApiListener {

    /**
     * 系统登录过程回调。<br>
     * 此函数为Login()登录函数的回调，调用Login()成功后建立了链路连接，然后API将向服务器发送登录认证信息，登录期间的数据发送情况和登录的回馈信息传递到此回调函数中。
     *
     * @param errorCode 返回错误码,0表示成功。
     * @param info      登陆应答信息，如果errorCode!=0，则info=NULL。
     */
    public void onRspLogin(int errorCode, TapAPIQuotLoginRspInfo info);

    /**
     * 返回所有品种信息。<br>
     * 此回调接口用于向用户返回得到的所有品种信息。
     *
     * @param sessionID 请求的会话ID
     * @param errorCode 错误码，当errorCode!=0时,info为NULL；
     * @param isLast    标示是否是最后一批数据；
     * @param info      品种信息。
     */
    public void onRspQryCommodity(int sessionID, int errorCode, boolean isLast, TapAPIQuoteCommodityInfo info);

    /**
     * 返回系统中合约信息
     *
     * @param sessionID 请求的会话ID；
     * @param errorCode 错误码，当errorCode!=0时,info为NULL；
     * @param isLast    标示是否是最后一批数据；
     * @param info      合约信息。当errorCode不为0时，info为空。
     */
    public void onRspQryContract(int sessionID, int errorCode, boolean isLast, TapAPIQuoteContractInfo info);

    /**
     * 返回订阅行情的全文。<br>
     * 此回调接口用来返回订阅行情的全文。全文为当前时间的行情信息。
     *
     * @param sessionID 请求的会话ID；
     * @param errorCode 错误码，当errorCode!=0时,info为NULL；
     * @param isLast    标示是否是最后一批数据；
     * @param info      行情信息。当errorCode不为0时，info为空。
     */
    public void onRspSubscribeQuote(int sessionID, int errorCode, boolean isLast, TapAPIQuoteWhole quoteWhole);

    /**
     * 退订指定合约的行情的结果回调
     *
     * @param info      指向返回的信息结构体。当errorCode不为0时，info为空。
     * @param isLast    标示是否是最后一批数据；
     * @param errorCode 错误码，当errorCode!=0时,info为NULL；
     * @param sessionID 请求的会话ID；
     */
    public void onRspUnSubscribeQuote(int sessionID, int errorCode, boolean isLast, TapAPIContract info);

    /**
     * 返回订阅行情的变化内容。<br>
     * 此回调接口用来通知用户行情信息产生了变化，并向用户提交新的行情全文。
     *
     * @param info 最新的行情全文内容
     */
    public void onRtnQuote(TapAPIQuoteWhole info);

}
