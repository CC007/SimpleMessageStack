/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.simplemessagestack.protocols;

import com.github.cc007.osimodel.Address;
import com.github.cc007.osimodel.HeaderType;
import com.github.cc007.osimodel.HeaderTypes;
import com.github.cc007.osimodel.PerformanceStatistics;
import com.github.cc007.osimodel.exceptions.HeaderTypesClassException;
import com.github.cc007.osimodel.protocols.datalinkLayer.DatalinkLayerProtocol;
import com.github.cc007.osimodel.protocols.networkLayer.NetworkLayerProtocol;
import com.github.cc007.simplemessagestack.DataLinkLayerProtocolHeaderTypes;
import com.github.cc007.simplemessagestack.exceptions.checksumErrorException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.CRC32;

/**
 *
 * @author Rik
 */
public class SimpleBinaryDatalinkLayerProtocol extends DatalinkLayerProtocol {

    public static final Class<DataLinkLayerProtocolHeaderTypes> DEFAULT_HEADER_TYPES = DataLinkLayerProtocolHeaderTypes.class;
    public static final int SOURCE_ADDRESS_SIZE = 0;
    public static final int DESTINATION_ADDRESS_SIZE = 0;
    public static final int PROTOCOLTYPE_SIZE = 2;
    public static final int MIN_DATAGRAM_PART_SIZE = 1;
    public static final int MAX_DATAGRAM_PART_SIZE = 1500;
    public static final int CHECKSUM_SIZE = 4;

    protected NetworkLayerProtocol datagram;
    protected ArrayList<Integer> checksum;
    protected HeaderType nextHeaderType;

    public SimpleBinaryDatalinkLayerProtocol() {
    }

    @Override
    public void setDatagram(NetworkLayerProtocol datagram) {
        if (datagram == null) {
            this.datagram = null;
        } else {
            this.datagram = datagram;
            CRC32 crc = new CRC32();
            checksum = new ArrayList<>(datagram.collapseCount());
            for (int i = 0; i < datagram.collapseCount(); i++) {
                crc.update(datagram.collapse()[i]);
                this.checksum.add((int) crc.getValue());
            }
        }
    }

    @Override
    public NetworkLayerProtocol getDatagram() {
        return datagram;
    }

    @Override
    public byte[][] collapse() {
        byte[][] datagramByteArray = datagram.collapse();
//// This could be useful for the network layer
//        int collapseCount = 0;
//        for (byte[] datagramByteArrayElem : datagramByteArray) {
//            collapseCount += (datagramByteArrayElem.length - 1) / MAX_DATAGRAM_PART_SIZE + 1;
//        }
        int collapseCount = datagramByteArray.length;
        byte[][] frameByteArray = new byte[collapseCount][0];
        for (int i = 0; i < collapseCount; i++) {
            frameByteArray[i] = new byte[SOURCE_ADDRESS_SIZE + DESTINATION_ADDRESS_SIZE + PROTOCOLTYPE_SIZE + datagramByteArray[i].length + CHECKSUM_SIZE];
            System.arraycopy(getNextHeader().asByteArray(), 0, frameByteArray[i], SOURCE_ADDRESS_SIZE + DESTINATION_ADDRESS_SIZE, PROTOCOLTYPE_SIZE);
            System.arraycopy(datagramByteArray[i], 0, frameByteArray[i], SOURCE_ADDRESS_SIZE + DESTINATION_ADDRESS_SIZE + PROTOCOLTYPE_SIZE, datagramByteArray[i].length);
            System.arraycopy(ByteBuffer.allocate(CHECKSUM_SIZE).putInt(checksum.get(i)).array(), 0, frameByteArray[i], SOURCE_ADDRESS_SIZE + DESTINATION_ADDRESS_SIZE + PROTOCOLTYPE_SIZE + datagramByteArray[i].length, CHECKSUM_SIZE);
        }
        return frameByteArray;
    }

    @Override
    public int collapseCount() {
        byte[][] frameByteArray = this.collapse();
        return frameByteArray.length;
    }

    @Override
    public void expand(byte[][] collapsedObject, Class<? extends HeaderTypes> headerTypesClass) throws HeaderTypesClassException {
        if (collapsedObject.length != 1) {
            throw new IllegalArgumentException("Only exactly one frame at a time is supported");
        } else {
            try {
                this.setNextHeader(headerTypesClass.newInstance().getHeaderType(ByteBuffer.wrap(collapsedObject[0], SOURCE_ADDRESS_SIZE + DESTINATION_ADDRESS_SIZE, SOURCE_ADDRESS_SIZE + DESTINATION_ADDRESS_SIZE + PROTOCOLTYPE_SIZE).getShort()));
                if (nextHeaderType.isLayerProtocol()) {
                    this.setDatagram((NetworkLayerProtocol) nextHeaderType.getNewLayerProtocolObject());
                    int datagramSize = collapsedObject[0].length - SOURCE_ADDRESS_SIZE - DESTINATION_ADDRESS_SIZE - PROTOCOLTYPE_SIZE - CHECKSUM_SIZE;
                    datagram.expand(new byte[][]{ByteBuffer.wrap(collapsedObject[0], SOURCE_ADDRESS_SIZE + DESTINATION_ADDRESS_SIZE + PROTOCOLTYPE_SIZE, datagramSize).array()}, null);
                    this.checksum = new ArrayList<>();
                    checksum.add(ByteBuffer.wrap(collapsedObject[0], SOURCE_ADDRESS_SIZE + DESTINATION_ADDRESS_SIZE + PROTOCOLTYPE_SIZE + datagramSize, CHECKSUM_SIZE).getInt());
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new HeaderTypesClassException("It was not possible to expand. The exception was caused by the provided HeaderTypes class", ex);
            }
        }
    }

    @Override
    public void correctErrors() throws checksumErrorException {
        boolean error = false;
        CRC32 crc = new CRC32();
        byte[][] datagramByteArray = this.datagram.collapse();
        if (datagramByteArray.length != this.checksum.size()) {
            error = true;
        }
        for (int i = 0; i < checksum.size(); i++) {
            crc.update(this.datagram.collapse()[i]);
            if (this.checksum.get(i) != (int) crc.getValue()) {
                error = true;
            }
        }
        if (error) {
            throw new checksumErrorException(this.getClass().getName() + "#" + this.hashCode() + ": There was an error with the received packet. No further action was taken");
        }
    }

    @Override
    public void setNextHeader(HeaderType headertype) {
        this.nextHeaderType = headertype;
    }

    @Override
    public HeaderType getNextHeader() {
        return nextHeaderType;
    }

    @Override
    public Address getSource() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This protocol doesnt use source/destination addressing!");
    }

    @Override
    public void setSource() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This protocol doesnt use source/destination addressing!");
    }

    @Override
    public Address getDestination() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This protocol doesnt use source/destination addressing!");
    }

    @Override
    public void setDestination(Address destination) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This protocol doesnt use source/destination addressing!");
    }

    @Override
    public PerformanceStatistics getPerformanceStatistics() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("No performance statistics were mesured!");
    }

}
