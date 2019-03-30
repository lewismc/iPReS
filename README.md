iPReS
=====

<img src="./logo/iPReS_logo.png" />

# Introduction
The Internationalization (i18n) Product Retrieval Service is a web service and client providing 
i18n-type access to products and product metadata contained within [NASA JPL's](http://www.jpl.nasa.gov/) 
[Physical Oceanography Distributed Active Archive Center](http://podaac.jpl.nasa.gov/) otherwise known as 
PO.DAAC.

The software implements a [RESTful](http://en.wikipedia.org/wiki/Representational_state_transfer) 
web-service and client to obtain data from the NASA JPL's [PO.DAAC Web-Services API](http://podaac.jpl.nasa.gov/ws/index.html). It then translates the product metadata into a target language provided along with the initial call to the service.

# Citation
There are a couple of ways to cite iPReS.

## IEEE 2015 Paper
```
L. J. McGibbney, K. D. Whitehall, C. A. Mattmann and P. M. Carter, "Enabling Linguistic Analysis of Scientific Metadata through Internationalizing NASA JPL's PODAAC," 2015 IEEE International Conference on Information Reuse and Integration, San Francisco, CA, 2015, pp. 207-210. doi: 10.1109/IRI.2015.41 - URL: http://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=7300977&isnumber=7300933
```
Note, if you cannot access the above paper, please check out the [IEEE IRI 2015 Poster](./docs/iri2015_ipres_poster.pdf)

## Zenodo

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.2617379.svg)](https://doi.org/10.5281/zenodo.2617379)

# Supported Product Translations
Approaching one hundred... see [here](https://tech.yandex.com/translate/doc/dg/concepts/api-overview-docpage/#api-overview__languages) for an out-the-box list.

It should be noted that because iPReS relies upon [Apache Tika's Translate module](https://github.com/apache/tika/tree/master/tika-translate), the supported translations differ depending on which translation service implementation you decide to use.
By default iPReS uses [Tika's YandexTranslator](https://github.com/apache/tika/blob/master/tika-translate/src/main/java/org/apache/tika/language/translate/YandexTranslator.java) meaning that you need to [create a Yandex API Key](https://translate.yandex.com/) (click login on top right to create account and API key). Once you have this key, simply edit [./app/resources/org/apache/tika/language/translate/translator.yandex.properties](https://github.com/lewismc/iPReS/blob/master/app/resources/org/apache/tika/language/translate/translator.yandex.properties) and you are good to go!

The following translation implementations are available

* [Google Translate](https://translate.google.com)
* [Apache Joshua](https://joshua.apache.org)
* [Lingo24](https://www.lingo24.com)
* [Bing (Microsoft) Translator](https://www.bing.com/Translator)
* [Moses statistical machine translation system](http://www.statmt.org/moses/)
* [Yandex](https://translate.yandex.com/)

In order to configure one of the above the steps are always the same
* Obtain credentials for using the target translation service
* Edit the relevant properties file in [./app/resources/org/apache/tika/language/translate/](https://github.com/lewismc/iPReS/blob/master/app/resources/org/apache/tika/language/translate/)
* Replace all instances of **YandexTranslator** in [./app/src/app/core.clj](https://github.com/lewismc/iPReS/blob/master/app/src/app/core.clj) with the tika-translate implementation you wish to use.

# Installation 
Please refer to the [Application Installation](https://github.com/lewismc/iPReS/tree/master/app) instructions. This also details the [prerequisite software](https://github.com/lewismc/iPReS/tree/master/app#prerequisites-for-local-development) installation requirements.

# Project Website
The project website can be located at - http://lewismc.github.io/iPReS/

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
