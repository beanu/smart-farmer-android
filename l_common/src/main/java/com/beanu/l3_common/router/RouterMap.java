package com.beanu.l3_common.router;

/**
 * @author liyi
 */
public class RouterMap {
    /**
     * 启动app
     */
    public static final String PATH_OPEN_APP = "/app/start";

    /**
     * 钱包相关页面
     */
    public class Wallet {
        /**
         * 钱包创建导入引导页面
         */
        public static final String PATH_WALLET_CREATE = "/wallet/create";

    }


    public class Service {

        public static final String SWAP_WALLET = "/service/swapwallet";

    }
}