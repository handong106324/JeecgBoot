package org.jeecg.modules.demo.utils;

import io.gate.gateapi.ApiClient;
import io.gate.gateapi.ApiException;
import io.gate.gateapi.api.*;
import io.gate.gateapi.models.*;

import java.util.List;
import java.util.Map;

public class GateApiUtils {
    public static void main(String[] args) {
        ApiClient client = new ApiClient();
        client.setApiKeySecret("4717da20946b7d7102a65563fb55d221", "733be1e46138cbe6f8c4ed07f6ff3bf773ce4ab7481671edf8a7b5c17cd7b491");
        AccountApi accountApi = new AccountApi(client);

        SpotApi spotApi = new SpotApi(client);
        WalletApi walletApi = new WalletApi(client);



//        spotApi.get

        try {


            for (SpotAccount spotAccount : spotApi.listSpotAccounts().execute()) {
                System.out.println(spotAccount.getCurrency() + ":" + spotAccount.getAvailable() + ":" + spotAccount.getLocked());
            }

        } catch (ApiException e) {
            throw new RuntimeException(e);
        }


    }
}
