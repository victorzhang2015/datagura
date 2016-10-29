
package com.antu.nmea.protobuf;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.antu.nmea.protobuf.GgaSentence.Sentence;
import com.google.protobuf.InvalidProtocolBufferException;

/**
* Pubsub envelope subscriber
*/

public class psenvsub {

    public static void main (String[] args) throws InvalidProtocolBufferException {

        // Prepare our context and subscriber
        Context context = ZMQ.context(1);
        Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://localhost:5563");
        subscriber.subscribe("A".getBytes());
        while (!Thread.currentThread ().isInterrupted ()) {
            // Read envelope with address
            String address = subscriber.recvStr ();
            // Read message contents
            byte[] contents = subscriber.recv();
            Sentence sentence2 = GgaSentence.Sentence.parseFrom(contents);
            sentence2.getEarth();
            System.out.println(address + "  Latitude : "+sentence2.getLatitude());
        }
        subscriber.close ();
        context.term ();
    }
}
