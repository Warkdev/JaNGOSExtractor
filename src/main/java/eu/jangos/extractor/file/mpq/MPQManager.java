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

import eu.jangos.extractor.file.exception.MPQException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import systems.crigges.jmpq3.MPQOpenOption;

/**
 * MPQManager is a class that must provide an unified view of several MPQ contained in a directory.
 * @author Warkdev
 */
public class MPQManager {

    private static final Logger logger = LoggerFactory.getLogger(MPQManager.class);

    private static final String EXTENSION_WDT = "wdt";
    private static final String EXTENSION_ADT = "adt";
    private static final String EXTENSION_WMO = "wmo";
    private static final String EXTENSION_M2 = "m2";

    private File directory;
    private Map<String, ExtendedMPQEditor> mapEditors;
    private List<String> listWMO;
    private List<String> listADT;
    private List<String> listWDT;
    private List<String> listM2;

    public MPQManager(String directory) throws MPQException {
        this.mapEditors = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        this.directory = new File(directory);
        this.listADT = new ArrayList<>();
        this.listWMO = new ArrayList<>();
        this.listWDT = new ArrayList<>();
        this.listM2 = new ArrayList<>();

        init();
    }

    private void init() throws MPQException {
        if(!directory.isDirectory()) {
            throw new MPQException("The provided path is not a directory");
        }

        buildListMpqEditors();
    }

    private void buildListMpqEditors() {
        clearEditors();

        ExtendedMPQEditor editor;
        String extension;
        for (String mpq : directory.list(new SuffixFileFilter(".mpq", IOCase.INSENSITIVE))) {
            try {
                editor = new ExtendedMPQEditor(new File(this.directory.getAbsoluteFile()+"/"+mpq), MPQOpenOption.READ_ONLY);
                for (String file : editor.getFileNames()) {
                    // If the file path already exist, we simply check if the MPQ is more recent.
                    file = file.toLowerCase();
                    if(this.mapEditors.containsKey(file)) {
                        if(this.mapEditors.get(file).getPatchValue() < editor.getPatchValue()) {
                            this.mapEditors.put(file, editor);
                        }
                    } else {
                        this.mapEditors.put(file, editor);
                        extension = FilenameUtils.getExtension(file);
                        switch(extension.toLowerCase()) {
                            case EXTENSION_WDT:
                                this.listWDT.add(file);
                                break;
                            case EXTENSION_ADT:
                                this.listADT.add(file);
                                break;
                            case EXTENSION_WMO:
                                // Excluding non-root WMO.
                                String group = FilenameUtils.getBaseName(file);
                                if(!StringUtils.isNumeric(group.substring(group.length() - 3))) {
                                    this.listWMO.add(file);
                                }
                                break;
                            case EXTENSION_M2:
                                this.listM2.add(file);
                                break;
                        }
                    }

                }
            } catch(IOException ex) {
                logger.warn("Impossible to open MPQ file "+mpq+", the file will be discarded from merged loading.");
            }
        }
    }

    public ExtendedMPQEditor getMPQForFile(String file) throws MPQException {
        if(!this.mapEditors.containsKey(file)) {
            throw new MPQException("The requested file "+file+" is not contained in the loaded MPQs.");
        }

        return this.mapEditors.get(file);
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
        try {
            init();
        } catch (MPQException ex) {
            logger.error("Cannot initialize the MPQ Manager");
        }
    }

    public List<String> getListWMO() {
        return listWMO;
    }

    public void setListWMO(List<String> listWMO) {
        this.listWMO = listWMO;
    }

    public List<String> getListADT() {
        return listADT;
    }

    public void setListADT(List<String> listADT) {
        this.listADT = listADT;
    }

    public List<String> getListWDT() {
        return listWDT;
    }

    public void setListWDT(List<String> listWDT) {
        this.listWDT = listWDT;
    }

    public List<String> getListM2() {
        return listM2;
    }

    public void setListM2(List<String> listM2) {
        this.listM2 = listM2;
    }

    private void clearEditors() {
        for(ExtendedMPQEditor editor : this.mapEditors.values()) {
            try {
                editor.close();
            } catch (IOException ex) {
                logger.warn("Issue while trying to close the MPQ editor for: "+editor.getMpqArchive());
            }
        }
        listADT.clear();
        listM2.clear();
        listWDT.clear();
        listWMO.clear();
    }
}
