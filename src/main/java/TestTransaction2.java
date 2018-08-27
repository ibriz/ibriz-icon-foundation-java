import foundation.icon.icx.*;
import foundation.icon.icx.data.Address;
import foundation.icon.icx.data.Block;
import foundation.icon.icx.data.Bytes;
import foundation.icon.icx.data.IconAmount;
import foundation.icon.icx.transport.http.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.Request;
import foundation.icon.icx.transport.jsonrpc.RpcObject;
import foundation.icon.icx.transport.jsonrpc.RpcValue;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.web3j.crypto.CipherException;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class TestTransaction2 {

    public final String URL = "http://192.168.1.9:9000/api/v3";

    private IconService iconService;
    private Wallet wallet;


    private String scoreAddress = "cx02b13428a8aef265fbaeeb37394d3ae8727f7a19";
    private String toAddStr = "hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31"; // second address where the amount is being transfered


    public TestTransaction2() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        iconService = new IconService(new HttpProvider(httpClient, URL));
        File file = new File("C:\\Users\\morga\\Desktop\\Temp\\ICONFoundationTest1\\UTC--2018-08-24T13-35-41.671000000Z--hx65f6e18d378b57612a28f72acb97021eaa82aa5a.json");
        try {
            System.out.println("before loading wallet");
            wallet = KeyWallet.load("password", file);
            System.out.println("after loading wallet" + wallet.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    public void sendTransaction() throws IOException {
        BigInteger networkId = new BigInteger("3");

        long timestamp = System.currentTimeMillis() * 1000L;

        RpcObject params = new RpcObject.Builder()
                .put("_to", new RpcValue(toAddStr))
                .put("_value", new RpcValue("1000"))
                .build();

        Transaction transaction = TransactionBuilder.of(networkId)
                .from(wallet.getAddress())
                .to(new Address(scoreAddress))
                .stepLimit(new BigInteger("10000"))
                .timestamp(new BigInteger(Long.toString(timestamp)))
                .nonce(new BigInteger("10000"))
                .call("transfer")
                .params(params)
                .build();

        SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
        Bytes hash = iconService.sendTransaction(signedTransaction).execute();
        System.out.println("txHash:" + hash);


        /*
         * GET BALANCE OF CURRENT ACCOUNT
         * */
        getBalance(wallet.getAddress());


        /*
         * GET BALANCE OF THE SPECIFIED ACCOUNT
         * */
        Address specified_address = new Address("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
        getBalance(specified_address);


    }

    public void getBalance(Address address)throws IOException {
        BigInteger balance_specified = iconService.getBalance(address).execute();
        System.out.println("balance of address: " + address + ":" + balance_specified);
    }



    public static void main(String[] args) throws IOException {
        new TestTransaction2().sendTransaction();
    }
}
