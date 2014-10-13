/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.simplemessagestack;

import com.github.cc007.osimodel.HeaderType;
import com.github.cc007.osimodel.protocols.LayerProtocol;
import com.github.cc007.simplemessagestack.protocols.DummyNetworkLayerProtocol;
import java.nio.ByteBuffer;
/**
 *
 * @author Rik
 */
public class DummyNetworkLayerProtocolHeaderType implements HeaderType{

    @Override
    public byte[] asByteArray() {
        return ByteBuffer.allocate(2).putShort((short)-1).array();
    }

    @Override
    public boolean isLayerProtocol() {
        return true;
    }

    @Override
    public LayerProtocol getNewLayerProtocolObject() {
        return new DummyNetworkLayerProtocol();
    }
    
}
