import foundation.icon.icx.*;
import foundation.icon.icx.data.*;
import foundation.icon.icx.transport.http.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.Request;
import foundation.icon.icx.transport.jsonrpc.RpcItem;
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
        File file = new File("C:\\Users\\morga\\Desktop\\Temp\\ibriz-icon-foundation-java\\UTC--2018-08-24T13-35-41.671000000Z--hx65f6e18d378b57612a28f72acb97021eaa82aa5a.json");
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

    public void sendTransaction(String toAddress, String token_id) throws IOException {
        BigInteger networkId = new BigInteger("3");

        long timestamp = System.currentTimeMillis() * 1000L;
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";
        String toAddStr = toAddress; // second address where the amount is being transfered
        RpcObject params = new RpcObject.Builder()
                .put("_to", new RpcValue(toAddStr))
                .put("_tokenId", new RpcValue(token_id))
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

    public void approveTransaction(String toAddress, String token_id) throws IOException {
        BigInteger networkId = new BigInteger("3");

        long timestamp = System.currentTimeMillis() * 1000L;
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";
        String toAddStr = toAddress; // second address where the amount is being transfered
        RpcObject params = new RpcObject.Builder()
                .put("_to", new RpcValue(toAddStr))
                .put("_tokenId", new RpcValue(token_id))
                .build();

        Transaction transaction = TransactionBuilder.of(networkId)
                .from(wallet.getAddress())
                .to(new Address(scoreAddress))
                .stepLimit(new BigInteger("10000"))
                .timestamp(new BigInteger(Long.toString(timestamp)))
                .nonce(new BigInteger("10000"))
                .call("approve")
                .params(params)
                .build();

        SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
        Bytes hash = iconService.sendTransaction(signedTransaction).execute();
        System.out.println("txHash:" + hash);
    }

    public void getBalance(Address address) throws IOException {
        BigInteger balance_specified = iconService.getBalance(address).execute();
        System.out.println("balance of address: " + address + ":" + balance_specified);
    }

    public void createToken() throws IOException {
        BigInteger networkId = new BigInteger("3");

        long timestamp = System.currentTimeMillis() * 1000L;
        Address ownerAddress = wallet.getAddress();
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";
        String toAddStr = "hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31"; // second address where the amount is being transfered
        RpcObject params = new RpcObject.Builder()
                .put("_name", new RpcValue("Sagar"))
                .put("_age", new RpcValue("10"))
                .put("_gender", new RpcValue("M"))
                .put("_ipfs_handle", new RpcValue("1000"))
                .build();

        Transaction transaction = TransactionBuilder.of(networkId)
                .from(ownerAddress)
                .to(new Address(scoreAddress))
                .stepLimit(new BigInteger("10000"))
                .timestamp(new BigInteger(Long.toString(timestamp)))
                .nonce(new BigInteger("10000"))
                .call("create_idol")
                .params(params)
                .build();

        SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
        Bytes hash = iconService.sendTransaction(signedTransaction).execute();
        System.out.println("txHash:" + hash);
    }

    public void getAllTokensOf(String address) throws IOException {


        Address firstAddress = new Address(address);
        String scoreAddress = "cx0bce5bfe899c4beec7ea93f2000e16351191017e";

        RpcObject params = new RpcObject.Builder()
                .put("_owner", new RpcValue(firstAddress))
                .build();

        Call<RpcItem> call = new Call.Builder()
                .from( firstAddress)
                .to(new Address(scoreAddress))
                .method("get_tokens_of_owner")
                .params(params)
                .build();

        RpcItem result = iconService.call(call).execute();
        System.out.println(firstAddress+ " :result:"+result.asString());
    }

    public static void main(String[] args) throws IOException {
//        new TestTransaction2().createToken();
//        new TestTransaction2().createToken();
//        new TestTransaction2().createToken();

//        new TestTransaction2().getAllTokensOf("hx65f6e18d378b57612a28f72acb97021eaa82aa5a");
//        new TestTransaction2().getAllTokensOf("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");


        new TestTransaction2().getAllTokensOf("hx65f6e18d378b57612a28f72acb97021eaa82aa5a");
        System.out.println("\n=======================================\n");
        new TestTransaction2().getAllTokensOf("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
        System.out.println("\n=======================================\n");
        System.out.println("||| Approving the transaction");
        new TestTransaction2().approveTransaction("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31", "1");
        System.out.println("\n=======================================\n");
        System.out.println("||| Processing the transaction");
        new TestTransaction2().sendTransaction("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31", "1");
        System.out.println("\n=======================================\n");
        new TestTransaction2().getAllTokensOf("hx65f6e18d378b57612a28f72acb97021eaa82aa5a");
        System.out.println("\n=======================================\n");
        new TestTransaction2().getAllTokensOf("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");
        System.out.println("\n=======================================\n");
    }

}
