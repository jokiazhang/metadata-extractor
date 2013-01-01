/*
 * Copyright 2002-2013 Drew Noakes
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * More information about this project is available at:
 *
 *    http://drewnoakes.com/code/exif/
 *    http://code.google.com/p/metadata-extractor/
 */
package com.drew.metadata.exif;

import com.drew.lang.Rational;
import com.drew.lang.annotations.NotNull;
import com.drew.lang.annotations.Nullable;
import com.drew.metadata.TagDescriptor;

/**
 * Provides human-readable string representations of tag values stored in a {@link NikonType1MakernoteDirectory}.
 * <p/>
 * Type-1 is for E-Series cameras prior to (not including) E990.  For example: E700, E800, E900,
 * E900S, E910, E950.
 * <p/>
 * MakerNote starts from ASCII string "Nikon". Data format is the same as IFD, but it starts from
 * offset 0x08. This is the same as Olympus except start string. Example of actual data
 * structure is shown below.
 * <pre><code>
 * :0000: 4E 69 6B 6F 6E 00 01 00-05 00 02 00 02 00 06 00 Nikon...........
 * :0010: 00 00 EC 02 00 00 03 00-03 00 01 00 00 00 06 00 ................
 * </code></pre>
 *
 * @author Drew Noakes http://drewnoakes.com
 */
public class NikonType1MakernoteDescriptor extends TagDescriptor<NikonType1MakernoteDirectory>
{
    public NikonType1MakernoteDescriptor(@NotNull NikonType1MakernoteDirectory directory)
    {
        super(directory);
    }

    @Nullable
    public String getDescription(int tagType)
    {
        switch (tagType) {
            case NikonType1MakernoteDirectory.TAG_QUALITY:
                return getQualityDescription();
            case NikonType1MakernoteDirectory.TAG_COLOR_MODE:
                return getColorModeDescription();
            case NikonType1MakernoteDirectory.TAG_IMAGE_ADJUSTMENT:
                return getImageAdjustmentDescription();
            case NikonType1MakernoteDirectory.TAG_CCD_SENSITIVITY:
                return getCcdSensitivityDescription();
            case NikonType1MakernoteDirectory.TAG_WHITE_BALANCE:
                return getWhiteBalanceDescription();
            case NikonType1MakernoteDirectory.TAG_FOCUS:
                return getFocusDescription();
            case NikonType1MakernoteDirectory.TAG_DIGITAL_ZOOM:
                return getDigitalZoomDescription();
            case NikonType1MakernoteDirectory.TAG_CONVERTER:
                return getConverterDescription();
            default:
                return super.getDescription(tagType);
        }
    }

    @Nullable
    public String getConverterDescription()
    {
        return getIndexedDescription(NikonType1MakernoteDirectory.TAG_CONVERTER, "None", "Fisheye converter");
    }

    @Nullable
    public String getDigitalZoomDescription()
    {
        Rational value = _directory.getRational(NikonType1MakernoteDirectory.TAG_DIGITAL_ZOOM);
        if (value == null)
            return null;
        if (value.getNumerator() == 0) {
            return "No digital zoom";
        }
        return value.toSimpleString(true) + "x digital zoom";
    }

    @Nullable
    public String getFocusDescription()
    {
        Rational value = _directory.getRational(NikonType1MakernoteDirectory.TAG_FOCUS);
        if (value == null)
            return null;
        if (value.getNumerator() == 1 && value.getDenominator() == 0) {
            return "Infinite";
        }
        return value.toSimpleString(true);
    }

    @Nullable
    public String getWhiteBalanceDescription()
    {
        return getIndexedDescription(NikonType1MakernoteDirectory.TAG_WHITE_BALANCE,
            "Auto",
            "Preset",
            "Daylight",
            "Incandescence",
            "Florescence",
            "Cloudy",
            "SpeedLight"
        );
    }

    @Nullable
    public String getCcdSensitivityDescription()
    {
        return getIndexedDescription(NikonType1MakernoteDirectory.TAG_CCD_SENSITIVITY,
            "ISO80",
            null,
            "ISO160",
            null,
            "ISO320",
            "ISO100"
        );
    }

    @Nullable
    public String getImageAdjustmentDescription()
    {
        return getIndexedDescription(NikonType1MakernoteDirectory.TAG_IMAGE_ADJUSTMENT,
            "Normal",
            "Bright +",
            "Bright -",
            "Contrast +",
            "Contrast -"
        );
    }

    @Nullable
    public String getColorModeDescription()
    {
        return getIndexedDescription(NikonType1MakernoteDirectory.TAG_COLOR_MODE,
            1,
            "Color",
            "Monochrome"
        );
    }

    @Nullable
    public String getQualityDescription()
    {
        return getIndexedDescription(NikonType1MakernoteDirectory.TAG_QUALITY,
            1,
            "VGA Basic",
            "VGA Normal",
            "VGA Fine",
            "SXGA Basic",
            "SXGA Normal",
            "SXGA Fine"
        );
    }
}
