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
package eu.jangos.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Warkdev
 */
public class ByteBufferUtils {
    
    public static ByteBuffer getByteBuffer(String resource) throws FileNotFoundException, IOException {
        File file = new File(ByteBufferUtils.class.getClassLoader().getResource(resource).getFile());
        if(!file.exists()) {
            fail("File does not exist");
        }
        ByteBuffer in = ByteBuffer.wrap(new FileInputStream(file).readAllBytes());
        in.order(ByteOrder.LITTLE_ENDIAN);
        return in;
    }
}
