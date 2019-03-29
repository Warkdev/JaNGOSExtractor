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
package eu.jangos.extractor.file.mpq;

import java.io.File;
import org.apache.commons.io.FilenameUtils;
import systems.crigges.jmpq3.JMpqEditor;
import systems.crigges.jmpq3.JMpqException;
import systems.crigges.jmpq3.MPQOpenOption;

/**
 *
 * @author Warkdev
 */
public class ExtendedMPQEditor extends JMpqEditor {
    
    private static final String PATCH_SEPARATOR = "-";
    private String mpqArchive;
    
    public ExtendedMPQEditor(File mpqArchive, MPQOpenOption... openOptions) throws JMpqException {        
        super(mpqArchive.toPath(), openOptions);
        this.mpqArchive = mpqArchive.getAbsolutePath();
    }

    public String getMpqArchive() {
        return mpqArchive;
    }

    public void setMpqArchive(String mpqArchive) {
        this.mpqArchive = mpqArchive;
    }        
    
    /**
     * Provides the patch value from the MPQ filename.
     * E.g. 
     * - patch-2.mpq will return 2. 
     * - patch.mpq will return 1.
     * - wmo.mpq will return 0.
     * - patch-x.mpq will return x.
     * @return The patch value of the MPQ.
     */
    public int getPatchValue() {
        String shortName = FilenameUtils.removeExtension(FilenameUtils.getName(this.mpqArchive));
        if(shortName.startsWith("patch")) {
            String[] split = shortName.split(PATCH_SEPARATOR);            
            return (split.length == 1 ? 1 : Integer.parseInt(split[1]));
        }
        return 0;
    }
}
