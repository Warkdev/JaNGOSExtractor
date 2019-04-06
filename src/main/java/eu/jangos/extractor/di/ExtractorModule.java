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
package eu.jangos.extractor.di;

import dagger.Module;
import dagger.Provides;
import eu.jangos.extractor.file.mpq.MPQManager;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Warkdev
 */
@Module
public class ExtractorModule {
    
    private static final Logger logger = LoggerFactory.getLogger(ExtractorModule.class);
    
    private final String directory;
    
    public ExtractorModule(String directory) {
        this.directory = directory;
    }
        
    @Provides
    @Singleton
    public MPQManager provideMPQManager() {
        try {
            return new MPQManager(this.directory);
        } catch (Exception e) {
            logger.error("Error while generating MPQManager");
        }
        
        return null;
    }
    
}
