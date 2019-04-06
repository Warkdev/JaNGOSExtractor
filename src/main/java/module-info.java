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
module eu.jangos.extractor {
    requires static logback.classic;
    requires static logback.core;
    requires static slf4j.api;    
    requires static obj;
    requires static eu.jangos.utils;
    requires static org.apache.commons.io;
    requires static org.apache.commons.lang3;    
    requires static javafx.controls;            
    requires static systems.crigges.jmpq;
    requires static javafx.swing;
    requires static dagger;
    requires static javax.inject;
    
    exports eu.jangos.extractor.file;
    exports eu.jangos.extractor.file.exception;
    exports eu.jangos.extractor.file.impl;
    exports eu.jangos.extractor.rendering;
    exports eu.jangos.extractor.file.mpq;
    exports eu.jangos.extractor.di;
}
