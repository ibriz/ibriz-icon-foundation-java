

import foundation.icon.icx.*;
import foundation.icon.icx.data.Address;
import foundation.icon.icx.data.Block;
import foundation.icon.icx.data.Bytes;
import foundation.icon.icx.data.IconAmount;
import foundation.icon.icx.transport.http.HttpProvider;
import foundation.icon.icx.transport.jsonrpc.Request;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.web3j.crypto.CipherException;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class TestTransaction {
    public final String URL = "http://192.168.1.9:9000/api/v3";
    public final String PRIVATE_KEY_STRING =
            "2d42994b2f7735bbc93a3e64381864d06747e574aa94655c516f9ad0a74eed79";

    private IconService iconService;
    private Wallet wallet;

    public TestTransaction() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        iconService = new IconService(new HttpProvider(httpClient, URL));
        File file = new File("C:\\Users\\morga\\Desktop\\Temp\\ICONFoundationTest1\\UTC--2018-08-24T13-35-41.671000000Z--hx65f6e18d378b57612a28f72acb97021eaa82aa5a.json");
        try {
            wallet = KeyWallet.load("password", file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }



    public void sendTransaction() throws IOException {

        BigInteger networkId = new BigInteger("3");
//        Address fromAddress = wallet.getAddress();
        Address fromAddress = wallet.getAddress();
        Address toAddress = new Address("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");

        BigInteger value = IconAmount.of("0", IconAmount.Unit.ICX).toLoop();
        BigInteger stepLimit = new BigInteger("75000");
        long timestamp = System.currentTimeMillis() * 1000L;
        BigInteger nonce = new BigInteger("1");

        Transaction transaction = TransactionBuilder.of(networkId)
                .from(fromAddress)
                .to(toAddress)
                .value(value)
                .stepLimit(stepLimit)
                .timestamp(new BigInteger(Long.toString(timestamp)))
                .nonce(nonce)
                .build();

        SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
        Bytes hash = iconService.sendTransaction(signedTransaction).execute();
        System.out.println("txHash:"+hash);
    }
    /*public static void main(String[] args) {
        String URL = "http://192.168.1.9:9000/api/v3";
        Address scoreAddress = new Address("cxb8f2c9ba48856df2e889d1ee30ff6d2e002651cf");
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();
        IconService iconService = new IconService(new HttpProvider(httpClient, URL));
        BigInteger height = BigInteger.ONE;
        Block block = null;
        try {

            Address fromAddress = new Address("hxaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            Address toAddress = new Address("hx1000000000000000000000000000000000000000");

            *//*
             * CREATING TRANSACTION - TEST 2
             * *//*

            // Generates a wallet. - FIRST TIME
//            KeyWallet wallet = KeyWallet.create();
//            File dir = new File(".");
//            KeyWallet.store(wallet, "password", dir);

            *//*
            * LOAD KEYWALLET FROM FILE
            * *//*
            File file = new File("C:\\Users\\morga\\Desktop\\Temp\\ICONFoundationTest1\\UTC--2018-08-24T13-35-41.671000000Z--hx65f6e18d378b57612a28f72acb97021eaa82aa5a.json");
            Wallet wallet = KeyWallet.load("password", file);


//            cxb8f2c9ba48856df2e889d1ee30ff6d2e002651cf

            // sending icx
            BigInteger networkId = new BigInteger("3");
            fromAddress = wallet.getAddress();
            toAddress = new Address("hx4873b94352c8c1f3b2f09aaeccea31ce9e90bd31");

            BigInteger value = IconAmount.of("1", IconAmount.Unit.ICX).toLoop();
            BigInteger stepLimit = new BigInteger("75000");
            long timestamp = System.currentTimeMillis() * 1000L;
            BigInteger nonce = new BigInteger("1");

            Transaction transaction = TransactionBuilder.of(networkId)
                    .from(fromAddress)
                    .to(toAddress)
                    .value(value)
                    .stepLimit(stepLimit)
                    .timestamp(new BigInteger(Long.toString(timestamp)))
                    .nonce(nonce)
                    .build();

            SignedTransaction signedTransaction = new SignedTransaction(transaction, wallet);
//            Bytes hash = iconService.sendTransaction(signedTransaction).execute();
//            System.out.println("txHash:"+hash);


            Call<Bytes> request = iconService.sendTransaction(signedTransaction);

// Asynchronized request execution
            request.execute(new Callback<Bytes>() {
                @Override
                public void onSuccess(Bytes result) {
                    System.out.println("Successfully Transaction= " + result);
                }

                @Override
                public void onFailure(Exception exception) {
                    System.out.println("Failed Transaction= " + exception.getMessage());

                }

            });

        } catch ( IOException | CipherException e) {
            e.printStackTrace();
        }


    }*/

    public static void main(String[] args) throws IOException {
        new TestTransaction().sendTransaction();
    }
}
