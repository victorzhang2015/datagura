
package com.antu.nmea.protobuf;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.antu.nmea.protobuf.GgaSentence.Sentence;

/**
* Pubsub envelope publisher
*/

public class psenvpub {

    public static void main (String[] args) throws Exception {
        // Prepare our context and publisher
        Context context = ZMQ.context(1);
        Socket publisher = context.socket(ZMQ.PUB);
        GgaSentence.Sentence.Builder builder =  GgaSentence.Sentence.newBuilder();
        builder.setEarth("E");
        builder.setLatitude(123.60);
        builder.setLongitude(234.78);
        builder.setEarth2("N");
        builder.setUTCtime("2016-10-15");
        Sentence entence = builder.build();
        byte[] buf = entence.toByteArray();
        publisher.bind("tcp://*:5563");
        while (!Thread.currentThread ().isInterrupted ()) {
            // Write two messages, each with an envelope and content
            publisher.sendMore ("A");
            publisher.send (buf);
            publisher.sendMore ("B");
            publisher.send(buf);
        }
        publisher.close ();
        context.term ();
    }
}
