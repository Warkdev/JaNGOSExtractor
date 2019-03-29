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
package eu.jangos.extractor.file.common;

import javafx.geometry.Point3D;

public class Quaternion {
    private double w;
    private double x;
    private double y;
    private double z;

    public final double getW() {
        return w;
    }
    
    public final double getX() {
        return x;
    }
    
    public final double getY() {
        return y;
    }
    
    public final double getZ() {
        return z;
    }
    
    public void setW(double w) {
        this.w = w;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public void setZ(double z) {
        this.z = z;
    }
    
    public void set(float x, float y, float z, float w) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set(double x, double y, double z, double w) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set(Quaternion q) {
        set(q.getX(), q.getY(), q.getZ(), q.getW());
    }

    public Quaternion() {
        set(0.0, 0.0, 0.0, 1.0);
    }
    
    public Quaternion(double x, double y, double z, double w) {
        set(x, y, z, w);
    }
    
    public Quaternion(float x, float y, float z, float w) {
        set(x, y, z, w);
    }
    
    public Quaternion(Quaternion q) {
        set(q);
    }

    public double magnitude() {
        final double w = getW();
        final double x = getX();
        final double y = getY();
        final double z = getZ();

        return Math.sqrt(w * w + x * x + y * y + z * z);
    }

    public Quaternion normalize() {
        final double mag = magnitude();

        if (mag == 0.0) {
            return new Quaternion(0.0f, 0.0f, 0.0f, 0.0f);
        }

        return new Quaternion(            
            getX() / mag,
            getY() / mag,
            getZ() / mag,
            getW() / mag);
    }
    
    public Quaternion conjugate() {
        return new Quaternion(getW(), -getX(), -getY(), -getZ());
    }

    public Quaternion multiply(Quaternion q) {
        Point3D va = new Point3D(getX(), getY(), getZ());
        Point3D vb = new Point3D(q.getX(), q.getY(), q.getZ());
        double dotAB = va.dotProduct(vb);
        
        Point3D crossAB = va.crossProduct(vb);        
	
        return new Quaternion(
            getW() * q.getW() - dotAB,
            getW() * vb.getX() + q.getW() * va.getX() + crossAB.getX(),
            getW() * vb.getY() + q.getW() * va.getY() + crossAB.getY(),
            getW() * vb.getZ() + q.getW() * va.getZ() + crossAB.getZ());
    }
    
    /**
     * Convert a Quaternion representation to Euler angle rotation with float values.
     * @param q The quaternion to convert.
     * @return A 3D Vector with float elements.
     */    
    public static Point3D toEulerFloat(Quaternion q) {        
        float x = 0;
        float y, z;
        
	// fix roll near poles with this tolerance
	double pole = Math.PI / 2.0 - 0.05;

	y = (float) Math.asin(2.0 * (q.getW() * q.getY() - q.getX() * q.getZ()));

	if ((y < pole) && (y > -pole)) {
            x = (float) Math.atan2(2.0 * (q.getY() * q.getZ() + q.getW() * q.getX()),
                1.0 - 2.0 * (q.getX() * q.getX() + q.getY() * q.getY()));
	}

	z = (float) Math.atan2(2.0 * (q.getX() * q.getY() + q.getW() * q.getZ()),
            1.0 - 2.0 * (q.getY() * q.getY() + q.getZ() * q.getZ()));
        
        return new Point3D(x, y, z);
    }
    
    /**
     * Convert a Quaternion representation to Euler angle rotation with double values.
     * @param q The quaternion to convert.
     * @return A 3D Vector with double elements.
     */
    public static Point3D toEuler(Quaternion q) {
        double x = 0;
        double y, z;
        
	// fix roll near poles with this tolerance
	double pole = Math.PI / 2.0 - 0.05;

	y = Math.asin(2.0 * (q.getW() * q.getY() - q.getX() * q.getZ()));

	if ((y < pole) && (y > -pole)) {
            x = Math.atan2(2.0 * (q.getY() * q.getZ() + q.getW() * q.getX()),
                1.0 - 2.0 * (q.getX() * q.getX() + q.getY() * q.getY()));
	}

	z = Math.atan2(2.0 * (q.getX() * q.getY() + q.getW() * q.getZ()),
            1.0 - 2.0 * (q.getY() * q.getY() + q.getZ() * q.getZ()));
        
        return new Point3D(x, y, z);
    }    
    
    /**
     * Convert a 3D-double based vector to a Quaternion representation.
     * @param v The 3D-double vector to convert
     * @return A Quaternion object representing the corresponding 3D-Vector.     
     */
    public static Quaternion fromEuler(Point3D v) {
	double cosX2 = Math.cos(v.getX() / 2.0);
	double sinX2 = Math.sin(v.getX() / 2.0);
	double cosY2 = Math.cos(v.getY() / 2.0);
	double sinY2 = Math.sin(v.getY() / 2.0);
	double cosZ2 = Math.cos(v.getZ() / 2.0);
	double sinZ2 = Math.sin(v.getZ() / 2.0);
        
        Quaternion q = new Quaternion(
            cosX2 * cosY2 * cosZ2 + sinX2 * sinY2 * sinZ2,
            sinX2 * cosY2 * cosZ2 - cosX2 * sinY2 * sinZ2,
            cosX2 * sinY2 * cosZ2 + sinX2 * cosY2 * sinZ2,
            cosX2 * cosY2 * sinZ2 - sinX2 * sinY2 * cosZ2);

        return q.normalize();
    }
    
    @Override public String toString() {
        return "Quaternion [w = " + getW() + ", x = " + getX() + ", y = " + getY() + ", z = " + getZ() + "]";
    }
}
