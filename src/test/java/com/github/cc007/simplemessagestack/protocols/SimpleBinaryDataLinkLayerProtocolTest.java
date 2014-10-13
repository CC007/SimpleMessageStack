/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cc007.simplemessagestack.protocols;

import com.github.cc007.bitoperations.BitArray;
import com.github.cc007.osimodel.Address;
import com.github.cc007.osimodel.HeaderType;
import com.github.cc007.osimodel.PerformanceStatistics;
import com.github.cc007.osimodel.protocols.networkLayer.NetworkLayerProtocol;
import com.github.cc007.simplemessagestack.DummyNetworkLayerProtocolHeaderType;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rik
 */
public class SimpleBinaryDataLinkLayerProtocolTest {

    SimpleBinaryDatalinkLayerProtocol sbdlp;
    DummyNetworkLayerProtocol dnlp;
    DummyNetworkLayerProtocolHeaderType dnlpht;

    public SimpleBinaryDataLinkLayerProtocolTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        sbdlp = new SimpleBinaryDatalinkLayerProtocol();
        dnlp = new DummyNetworkLayerProtocol();
        dnlpht = new DummyNetworkLayerProtocolHeaderType();
        sbdlp.setDatagram(dnlp);
        sbdlp.setNextHeader(dnlpht);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setDatagram method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testSetDatagram() {
        System.out.println("setDatagram");
        System.out.println("complex setter: check if checksum is set properly");
        
        //setup expected result
        CRC32 crc = new CRC32();
        crc.update(dnlp.collapse()[0]);
        int expResult = (int) crc.getValue();
        System.out.println(" Expected result:\t" + expResult);
        
        //setup result
        int result = sbdlp.checksum[0];
        System.out.println(" Actual result:\t\t" + result);
        
        //assert
        assertEquals(expResult, result);
        System.out.println(" Test succeeded");
        System.out.println();
    }

    /**
     * Test of getDatagram method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testGetDatagram() {
        System.out.println("getDatagram");
        System.out.println(" Simple getter");
        System.out.println();
    }

    /**
     * Test of collapse method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testCollapse() {
        System.out.println("collapse");

        // setup expected result
        CRC32 crc = new CRC32();
        crc.update(dnlp.collapse()[0]);
        byte[][] expResult = new byte[1][7];
        System.arraycopy(ByteBuffer.allocate(2).putShort((short) -1).array(), 0, expResult[0], 0, 2);
        expResult[0][2] = 1;
        System.arraycopy(ByteBuffer.allocate(4).putInt((int) crc.getValue()).array(), 0, expResult[0], 3, 4);
        System.out.print(" Expected result:\t");

        for (int i = 0; i < expResult[0].length; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(BitArray.getBit(expResult[0], i * 8 + j));
            }
            System.out.print(" ");
        }

        //setup result
        byte[][] result = sbdlp.collapse();
        System.out.print("\n Actual result:\t\t");
        for (int i = 0; i < result[0].length; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(BitArray.getBit(result[0], i * 8 + j));
            }
            System.out.print(" ");
        }

        //assert
        assertArrayEquals(expResult, result);
        System.out.println("\n Test succeeded");
        System.out.println();
    }

    /**
     * Test of collapseCount method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testCollapseCount() {
        System.out.println("collapseCount");

        //setup expected result
        int expResult = 1;
        System.out.println(" Expected result:\t" + expResult);

        //setup result
        int result = sbdlp.collapseCount();
        System.out.println(" Actual result:\t\t" + result);

        //assert
        assertEquals(expResult, result);
        System.out.println(" Test succeeded");
        System.out.println();
    }

    /**
     * Test of expand method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testExpand() {
        System.out.println("expand");
        byte[][] collapsedObject = null;
        sbdlp.expand(collapsedObject);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of correctErrors method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testCorrectErrors() {
        System.out.println("correctErrors");
        sbdlp.correctErrors();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNextHeader method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testSetNextHeader() {
        System.out.println("setNextHeader");
        System.out.println(" Simple setter");
        System.out.println();
    }

    /**
     * Test of getNextHeader method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testGetNextHeader() {
        System.out.println("getNextHeader");
        System.out.println(" Simple getter");
        System.out.println();
    }

    /**
     * Test of getSource method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testGetSource() {
        System.out.println("getSource");

        //assert
        System.out.println(" Check if the exeption gets thrown:");
        try {
            Address result = sbdlp.getSource();
        } catch (UnsupportedOperationException ex) {
            //pass state
            System.out.println(" Test succeeded");
            System.out.println();
            return;
        }

        //fail state
        fail(" An UnsupportedOperationException should have been thrown");
    }

    /**
     * Test of setSource method, of class SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testSetSource() {
        System.out.println("setSource");

        //assert
        System.out.println(" Check if the exeption gets thrown:");
        try {
            sbdlp.setSource();
        } catch (UnsupportedOperationException ex) {
            //pass state
            System.out.println(" Test succeeded");
            System.out.println();
            return;
        }

        //fail state
        fail(" An UnsupportedOperationException should have been thrown");
    }

    /**
     * Test of getDestination method, of class
     * SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testGetDestination() {
        System.out.println("getDestination");

        //assert
        System.out.println(" Check if the exeption gets thrown:");
        try {
            Address result = sbdlp.getDestination();
        } catch (UnsupportedOperationException ex) {
            //pass state
            System.out.println(" Test succeeded");
            System.out.println();
            return;
        }

        //fail state
        fail(" An UnsupportedOperationException should have been thrown");
    }

    /**
     * Test of setDestination method, of class
     * SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testSetDestination() {
        System.out.println("setDestination");
        Address destination = null;
        //assert
        System.out.println(" Check if the exeption gets thrown:");
        try {
            sbdlp.setDestination(destination);
        } catch (UnsupportedOperationException ex) {
            //pass state
            System.out.println(" Test succeeded");
            System.out.println();
            return;
        }

        //fail state
        fail(" An UnsupportedOperationException should have been thrown");
    }

    /**
     * Test of getPerformanceStatistics method, of class
     * SimpleBinaryDatalinkLayerProtocol.
     */
    @Test
    public void testGetPerformanceStatistics() {
        System.out.println("getPerformanceStatistics");

        //assert
        System.out.println( " Check if the exeption gets thrown:");
        try {
            Address result = sbdlp.getDestination();
        } catch (UnsupportedOperationException ex) {
            //pass state
            System.out.println(" Test succeeded");
            System.out.println();
            return;
        }

        //fail state
        fail(" An UnsupportedOperationException should have been thrown");
    }

}
