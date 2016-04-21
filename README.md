iPReS
=====

<img src="./logo/iPReS_logo.png" />

#Introduction
The Internationalization (i18n) Product Retrieval Service is a web service and client providing 
i18n-type access to products and product metadata contained within [NASA JPL's](http://www.jpl.nasa.gov/) 
[Physical Oceanography Distributed Active Archive Center](http://podaac.jpl.nasa.gov/) otherwise known as 
PO.DAAC.

The software implements a [RESTful](http://en.wikipedia.org/wiki/Representational_state_transfer) 
[Apache CXF](http://cxf.apache.org) web-service and client to obtain data from the [PO.DAAC 
Web-Services API](http://podaac.jpl.nasa.gov/ws/index.html). It then translates the product metadata into 
a target language provided along with the intial call to the service. 

#Project Website
The project website can be located at - http://lewismc.github.io/iPReS/

# Installation 
Please refer to the [Application Installation](https://github.com/lewismc/iPReS/tree/master/app) instructions. This also details the [prerequisite software](https://github.com/lewismc/iPReS/tree/master/app#prerequisites-for-local-development) installation requirements.

#Supported Product Translations
We aim to support the following list of language translations:

 * ar == Arabic
 * bg == Bulgarian
 * ca == Catalan
 * zh-CHS == Chinese Simplified
 * zh-CHT == Chinese Traditional
 * cs == Czech
 * da == Danish
 * nl == Dutch
 * en == English
 * et == Estonian
 * fi == Finnish
 * fr == French
 * de == German
 * el == Greek
 * ht == Haitian Creole
 * he == Hebrew
 * hi == Hindi
 * mww == Hmong Daw
 * hu == Hungarian
 * id == Indonesian
 * it == Italian
 * ja == Japanese
 * tlh == Klingon
 * tlh-Qaak == Klingon (pIqaD)
 * ko == Korean
 * lv == Latvian
 * lt == Lithuanian
 * ms == Malay
 * mt == Maltese
 * no == Norwegian
 * fa == Persian
 * pl == Polish
 * pt == Portuguese
 * ro == Romanian
 * ru == Russian
 * sk == Slovak
 * sl == Slovenian
 * es == Spanish
 * sv == Swedish
 * th == Thai
 * tr == Turkish
 * uk == Ukrainian
 * ur == Urdu
 * vi == Vietnamese
 * cy == Welsh
 
# Community, Support and Development

## Community Mailing List
Please visit and subscribe to our [Google Group](https://groups.google.com/forum/#!forum/ipres-capstone) for project news and development.

## Issue Tracking
Please open a ticket in the [issue tracker](https://github.com/lewismc/iPReS/issues).
Please use [labels](https://help.github.com/articles/applying-labels-to-issues-and-pull-requests/) to
classify your issue.

# License
iPReS is licensed permissively under the [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0).
A copy of that license is distributed with this software.

# Copyright and Export Classification
```
Copyright 2016, by the California Institute of Technology. ALL RIGHTS RESERVED.
United States Government Sponsorship acknowledged. Any commercial use must be
negotiated with the Office of Technology Transfer at the California Institute
of Technology.
This software may be subject to U.S. export control laws. By accepting this software,
the user agrees to comply with all applicable U.S. export laws and regulations.
User has the responsibility to obtain export licenses, or other export authority
as may be required before exporting such information to foreign countries or
providing access to foreign persons.
```