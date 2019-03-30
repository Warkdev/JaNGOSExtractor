/*
 * Copyright (C) 2019 Warkdev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.jangos.extractor.file.adt.chunk;

import eu.jangos.test.utils.ByteBufferUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Warkdev
 */
public class MCINTest {
    
    public MCINTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }    
    
    /**
     * Test of read method, of class MCIN.
     */
    @Test
    public void testRead() throws FileNotFoundException, IOException {
        System.out.println("read");
        
        MCIN instance = new MCIN();
        ByteBuffer in = ByteBufferUtils.getByteBuffer("legit/adt/MCIN/mcin001");
        instance.read(in);
        assertEquals(18, instance.getOffsetMCNK());
        assertEquals(38, instance.getSize());
        assertEquals(0, instance.getFlags());
        assertEquals(0, instance.getAsyncId());
    }
    
    @Test
    public void testReadWrongMCIN() throws FileNotFoundException, IOException {
        System.out.println("read");
        
        MCIN instance = new MCIN();
        ByteBuffer in = ByteBufferUtils.getByteBuffer("legit/adt/MCIN/mcin002");
        assertThrows(BufferUnderflowException.class, () -> {
            instance.read(in);
        });        
    }
}
