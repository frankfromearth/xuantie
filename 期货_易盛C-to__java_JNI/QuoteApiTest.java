

import com.future.api.es.external.common.bean.TapAPICommodity;
import com.future.api.es.external.common.bean.TapAPIContract;
import com.future.api.es.external.common.constants.ErrorMsg;
import com.future.api.es.external.quote.QuoteApi;
import com.future.api.es.external.quote.bean.TapAPIQuotLoginRspInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteCommodityInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteContractInfo;
import com.future.api.es.external.quote.bean.TapAPIQuoteLoginAuth;
import com.future.api.es.external.quote.bean.TapAPIQuoteWhole;
import com.future.api.es.external.quote.listener.QuoteApiListener;

public class QuoteApiTest {

    private final static String authCode = "67EA896065459BECDFDB924B29CB7DF1946CED32E26C1EAC946CED32E26C1EAC946CED32E26C1EAC946CED32E26C1EAC5211AF9FEE541DDE41BCBAB68D525B0D111A0884D847D57163FF7F329FA574E7946CED32E26C1EAC946CED32E26C1EAC733827B0CE853869ABD9B8F170E14F8847D3EA0BF4E191F5D97B3DFE4CCB1F01842DD2B3EA2F4B20CAD19B8347719B7E20EA1FA7A3D1BFEFF22290F4B5C43E6C520ED5A40EC1D50ACDF342F46A92CCF87AEE6D73542C42EC17818349C7DEDAB0E4DB16977714F873D505029E27B3D57EB92D5BEDA0A710197EB67F94BB1892B30F58A3F211D9C3B3839BE2D73FD08DD776B9188654853DDA57675EBB7D6FBBFC";
    private final static String ip = "123.15.58.21";
    private final static short port = (short) 7171;

    public static void main(String[] args) {
        final QuoteApi quoteApi = new QuoteApi(authCode, "", true);
        System.out.println(QuoteApi.getVersion());
        quoteApi.setListener(new QuoteApiListener() {

            /**
             * 通知用户API准备就绪。<br>
             * 只有用户回调收到此就绪通知时才能进行后续的各种行情数据查询操作。此回调函数是API能否正常工作的标志。<br>
             * 就绪后才可以进行后续正常操作
             */
            public void onAPIReady() {
                System.out.println("API初始化成功");
//				quoteApi.qryCommodity();
//				quoteApi.qryContract(new TapAPICommodity("NYMEX", 'F', "CL"));
                quoteApi.subscribeQuote(new TapAPIContract(new TapAPICommodity("NYMEX", 'F', "CL"), "1805", null, 'N', null, null, 'N'));
            }

            /**
             * API和服务失去连接的回调 <br>
             * 在API使用过程中主动或者被动与服务器服务失去连接后都会触发此回调通知用户与服务器的连接已经断开。
             *
             * @param reasonCode
             *            断开原因代码。具体原因请参见错误码列表
             */
            public void onDisconnected(int reasonCode) {

            }

            /**
             * 系统登录过程回调。<br>
             * 此函数为Login()登录函数的回调，调用Login()成功后建立了链路连接，然后API将向服务器发送登录认证信息，登录期间的数据发送情况和登录的回馈信息传递到此回调函数中。
             *
             * @param errorCode
             *            返回错误码,0表示成功。
             * @param info
             *            登陆应答信息，如果errorCode!=0，则info=NULL。
             */
            public void onRspLogin(int errorCode, TapAPIQuotLoginRspInfo info) {
                if (errorCode == 0) {
                    System.out.println("登录成功");
                } else {
                    System.out.println("登录失败 " + ErrorMsg.getErrorMsg(errorCode));
                    System.exit(-2);
                }
            }

            /**
             * 返回系统中品种信息<br>
             * 此回调接口用于向用户返回得到的所有品种信息。
             * @param sessionID 请求的会话ID，和GetAllCommodities()函数返回对应；
             * @param errorCode 错误码。0 表示成功。
             * @param isLast    标示是否是最后一批数据；
             * @param info        指向返回的信息结构体。当errorCode不为0时，info为空。
             */
            public void onRspQryCommodity(int sessionID, int errorCode, boolean isLast, TapAPIQuoteCommodityInfo info) {
                System.out.println(info);
            }

            /**
             * 返回系统中合约信息
             *
             * @param sessionID
             *            请求的会话ID；
             * @param errorCode
             *            错误码，当errorCode!=0时,info为NULL；
             * @param isLast
             *            标示是否是最后一批数据；
             * @param info
             *            合约信息。当errorCode不为0时，info为空。
             */
            public void onRspQryContract(int sessionID, int errorCode, boolean isLast, TapAPIQuoteContractInfo info) {
                if (errorCode == 0) {
                    System.out.println(info);
                } else {
                    System.out.println(ErrorMsg.getErrorMsg(errorCode));
                }
            }

            /**
             * 返回订阅行情的全文。<br>
             * 此回调接口用来返回订阅行情的全文。全文为当前时间的行情信息。
             *
             * @param sessionID
             *            请求的会话ID；
             * @param errorCode
             *            错误码，当errorCode!=0时,info为NULL；
             * @param isLast
             *            标示是否是最后一批数据；
             * @param info
             *            行情信息。当errorCode不为0时，info为空。
             */
            public void onRspSubscribeQuote(int sessionID, int errorCode, boolean isLast, TapAPIQuoteWhole quoteWhole) {
                if (errorCode == 0) {
                    System.out.println(quoteWhole.getContract() + "订阅成功");
                    System.out.println(quoteWhole);
                } else {
                    System.out.println("订阅失败 " + ErrorMsg.getErrorMsg(errorCode));
                }
            }

            /**
             * 退订指定合约的行情的结果回调
             *
             * @param info
             *            指向返回的信息结构体。当errorCode不为0时，info为空。
             * @param isLast
             *            标示是否是最后一批数据；
             * @param errorCode
             *            错误码，当errorCode!=0时,info为NULL；
             * @param sessionID
             *            请求的会话ID；
             */
            public void onRspUnSubscribeQuote(int sessionID, int errorCode, boolean isLast, TapAPIContract info) {

            }

            /**
             * 返回订阅行情的变化内容。<br>
             * 此回调接口用来通知用户行情信息产生了变化，并向用户提交新的行情全文。
             *
             * @param info
             *            最新的行情全文内容
             */
            public void onRtnQuote(TapAPIQuoteWhole quoteWhole) {
                System.out.println(quoteWhole);
            }

        });
        quoteApi.setHostAddress(ip, port);
        quoteApi.login(new TapAPIQuoteLoginAuth("ESUNNY", 'N', "Es123456", null, null, 'N', null));
    }
}
