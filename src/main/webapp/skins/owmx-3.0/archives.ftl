<#--

    Solo - A small and beautiful blogging system written in Java.
    Copyright (c) 2010-present, b3log.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

-->
<#include "../../common-template/macro-common_head.ftl">
<!DOCTYPE html>
<html>
    <head>
        <@head title="${archiveLabel} - ${blogTitle}">
            <link rel="stylesheet" href="${staticServePath}/skins/${skinDirName}/css/base.css?${staticResourceVersion}"/>
        </@head>
    </head>
    <body>
        ${topBarReplacement}
        <div id="a">
            <#include "header.ftl">
            <div id="b">
                <article>
                    <#if 0 != archiveDates?size>
                        <ul id="tags">
                        <#list archiveDates as archiveDate>
                            <li>
                                <#if "en" == localeString?substring(0, 2)>
                                    <a class="post-title" href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}">
                                        ${archiveDate.monthName} ${archiveDate.archiveDateYear}(${archiveDate.archiveDatePublishedArticleCount})
                                    </a>
                                <#else>
                                        <a class="post-title" href="${servePath}/archives/${archiveDate.archiveDateYear}/${archiveDate.archiveDateMonth}">
                                            ${archiveDate.archiveDateYear} ${yearLabel} ${archiveDate.archiveDateMonth} ${monthLabel}(${archiveDate.archiveDatePublishedArticleCount})
                                        </a>
                                </#if>
                            </li>
                        </#list>
                        </ul>
                    </#if>
                </article>
                <#include "side.ftl">
                <div class="clear"></div>
            </div>
            <#include "footer.ftl">
        </div>
        <script type="text/javascript">
            Util.buildTags();
        </script>
    </body>
</html>
