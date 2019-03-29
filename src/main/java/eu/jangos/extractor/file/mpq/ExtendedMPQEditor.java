/* 
 * Copyright 2018 Warkdev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
