/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.simplemessagestack;

import com.github.cc007.osimodel.HeaderType;
import com.github.cc007.osimodel.HeaderTypes;

/**
 *
 * @author Rik
 */
public class DataLinkLayerProtocolHeaderTypes implements HeaderTypes{

    @Override
    public HeaderType getHeaderType(Number headerCode) {
        if(headerCode.shortValue() == -1){
            return new DummyNetworkLayerProtocolHeaderType();
        }
        return null;
    }
    
}
