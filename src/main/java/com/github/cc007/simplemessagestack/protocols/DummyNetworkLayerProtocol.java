/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.simplemessagestack.protocols;

import com.github.cc007.osimodel.Address;
import com.github.cc007.osimodel.HeaderType;
import com.github.cc007.osimodel.HeaderTypes;
import com.github.cc007.osimodel.exceptions.HeaderTypesClassException;
import com.github.cc007.osimodel.protocols.networkLayer.NetworkLayerProtocol;
import com.github.cc007.osimodel.protocols.transportLayer.TransportLayerProtocol;

/**
 *
 * @author Rik
 */
public class DummyNetworkLayerProtocol extends NetworkLayerProtocol {

    public DummyNetworkLayerProtocol() {
    }

    @Override
    public void setSegment(TransportLayerProtocol segment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TransportLayerProtocol getSegment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[][] collapse() {
        byte[][] datagramByteArray = new byte[1][1];
        datagramByteArray[0][0] = 1;
        return datagramByteArray;
    }

    @Override
    public int collapseCount() {
        return 1;
    }

    @Override
    public void expand(byte[][] collapsedObject, Class<? extends HeaderTypes> headerTypesClass) throws HeaderTypesClassException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Address getSource() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSource() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Address getDestination() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDestination(Address destination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setNextHeader(HeaderType headertype) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HeaderType getNextHeader() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
