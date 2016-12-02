// IMyAidlInterface.aidl
package com.vij.vijaidlservice;

// Declare any non-default types here with import statements

interface ICalculator {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

   int add(int x, int y);

   int sub(int x, int y);

   int mul(int x, int y);

   String LoginDetails();

}
