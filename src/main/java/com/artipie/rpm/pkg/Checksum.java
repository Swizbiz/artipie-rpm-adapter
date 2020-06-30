/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 artipie.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.artipie.rpm.pkg;

import com.artipie.rpm.Digest;
import java.io.IOException;

/**
 * RPM checksum.
 * @since 0.6
 */
public interface Checksum {

    /**
     * Digest.
     * @return Digest
     */
    Digest digest();

    /**
     * Checksum hex string.
     * @return Hex string
     * @throws IOException On error
     */
    String hex() throws IOException;

    /**
     * Simple {@link Checksum} implementation.
     * @since 0.11
     */
    final class Simple implements Checksum {

        /**
         * Digest.
         */
        private final Digest dgst;

        /**
         * Checksum hex.
         */
        private final String sum;

        /**
         * Ctor.
         * @param dgst Digest
         * @param sum Checksum hex
         */
        public Simple(final Digest dgst, final String sum) {
            this.dgst = dgst;
            this.sum = sum;
        }

        @Override
        public Digest digest() {
            return this.dgst;
        }

        @Override
        public String hex() throws IOException {
            return this.sum;
        }
    }
}
