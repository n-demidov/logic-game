package edu.test;

import java.util.function.Function;

public class PartHasher {

    public static void main(String[] args) {
        PartHasher partHasher = new PartHasher();

        System.out.println(partHasher.fraudPartitionMapper());
    }

    public int getTurnoversPartition() {
        int numPartitions = 1024;
        String merchantIdRaw = "<your_number>";
        String terminalIdRaw = "<your_number";

//        String merchantId = StringMapper.nonNullOrEmpty(merchantIdRaw);
//        String terminalId = StringMapper.nonNullOrEmpty(terminalIdRaw);

        String merchantId = "101000014929";
        String terminalId = "20119683";

//        String k = merchantId + '@' + terminalId;
        String k = merchantId + '@' + terminalId + '@' + "202003";

        return Math.abs(k.hashCode() % numPartitions);
    }

    public int fraudPartitionMapper() {
        int numPartitions = 1024;

        String merchantId = "101000014929";
        String terminalId = "20119683";
        String countryCode = "RU";

        String k = merchantId + '@' + terminalId + '@' + countryCode;
        return Math.abs(k.hashCode() % numPartitions);
    }

}
