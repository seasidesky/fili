// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.bard.webservice.web.apirequest;

import com.yahoo.bard.webservice.data.dimension.Dimension;
import com.yahoo.bard.webservice.data.dimension.DimensionDictionary;
import com.yahoo.bard.webservice.data.dimension.DimensionField;
import com.yahoo.bard.webservice.data.filterbuilders.DruidFilterBuilder;
import com.yahoo.bard.webservice.data.metric.LogicalMetric;
import com.yahoo.bard.webservice.data.time.Granularity;
import com.yahoo.bard.webservice.druid.model.having.Having;
import com.yahoo.bard.webservice.druid.model.orderby.OrderByColumn;
import com.yahoo.bard.webservice.table.LogicalTable;
import com.yahoo.bard.webservice.web.ApiFilter;
import com.yahoo.bard.webservice.web.ApiHaving;
import com.yahoo.bard.webservice.web.ResponseFormatType;
import com.yahoo.bard.webservice.web.filters.ApiFilters;
import com.yahoo.bard.webservice.web.util.PaginationParameters;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

import javax.ws.rs.core.UriInfo;

/**
 * DataApiRequest Request binds, validates, and models the parts of a request to the data endpoint.
 */
 public interface DataApiRequest extends ApiRequest {

    String REQUEST_MAPPER_NAMESPACE = "dataApiRequestMapper";
    String RATIO_METRIC_CATEGORY = "Ratios";
    String DATE_TIME_STRING = "dateTime";

    /**
     * The having constraints for this request, grouped by logical metrics.
     *
     * @return a map of havings by metrics.
     */
     Map<LogicalMetric, Set<ApiHaving>> getHavings();

    /**
     *  The fact model having (should probably remove this).
     *
     *  @return A fact model having
     */
     Having getHaving();

    /**
     * A prioritized list of sort columns.
     *
     * @return sort columns.
     */
     LinkedHashSet<OrderByColumn> getSorts();

    /**
     * An optional limit of records returned.
     *
     * @return An optional integer.
     */
     OptionalInt getCount();

    /**
     * The limit per time bucket for a top n query.
     *
     * @return The number of values per bucket.
     */
     OptionalInt getTopN();

    /**
     * The date time zone to apply to the dateTime parameter and to express the response and granularity in.
     *
     * @return A time zone
     */
     DateTimeZone getTimeZone();

    /**
     * A filter builder (remove).
     *
     * @return a filter builder.
     */
     DruidFilterBuilder getFilterBuilder();

    /**
     * An optional sorting predicate for the time column.
     *
     * @return The sort direction
     */
     Optional<OrderByColumn> getDateTimeSort();

    /**
     * Get the dimensions used in filters on this request.
     *
     * @return A set of dimensions
     */
     Set<Dimension> getFilterDimensions();

    /**
     * The logical table for this request.
     *
     * @return A logical table
     */
     LogicalTable getTable();

    /**
     * The grain to group the results of this request.
     *
     * @return a granularity
     */
     Granularity getGranularity();

    /**
     * The set of grouping dimensions on this ApiRequest.
     *
     * @return a set of dimensions
     */
     Set<Dimension> getDimensions();

    /**
     * A map of dimension fields specified for the output schema.
     *
     * @return  The dimension fields for output grouped by their dimension
     */
     LinkedHashMap<Dimension, LinkedHashSet<DimensionField>> getDimensionFields();

    /**
     * The logical metrics requested in this query.
     *
     * @return A set of logical metrics
     */
     Set<LogicalMetric> getLogicalMetrics();

    /**
     * The intervals for this query.
     *
     * @return A set of intervals
     */
     Set<Interval> getIntervals();

    /**
     * The filters for this ApiRequest, grouped by dimensions.
     *
     * @return a map of filters by dimension
     */
     ApiFilters getApiFilters();

    /**
     * Generates filter objects on the based on the filter query in the api request.
     *
     * @param filterQuery  Expects a URL filter query String in the format:
     * (dimension name).(fieldname)-(operation):[?(value or comma separated values)]?
     * @param table  The logical table for the data request
     * @param dimensionDictionary  DimensionDictionary
     *
     * @return Set of filter objects.
     */
    Map<Dimension, Set<ApiFilter>> generateFilters(
            String filterQuery,
            LogicalTable table,
            DimensionDictionary dimensionDictionary
    );

    // CHECKSTYLE:OFF

    DataApiRequest withFormat(ResponseFormatType format);

    /**
     * Wither to replace pagination parameters.
     * An Optional empty will replace pagination parameters with the underlying default pagination parameters.
     *
     * @param paginationParameters  Optional of pagination parameters or empty to use the default.
     *
     * @return  A copy of this DataApiRequest with modified pagination parameters.
     */
    DataApiRequest withPaginationParameters(Optional<PaginationParameters> paginationParameters);

    DataApiRequest withUriInfo(UriInfo uriInfo);

    DataApiRequest withTable(LogicalTable table);

    DataApiRequest withGranularity(Granularity granularity);

    DataApiRequest withDimensions(Set<Dimension> dimensions);

    DataApiRequest withPerDimensionFields(LinkedHashMap<Dimension,
            LinkedHashSet<DimensionField>> perDimensionFields);

    DataApiRequest withLogicalMetrics(Set<LogicalMetric> logicalMetrics);

    DataApiRequest withIntervals(Set<Interval> intervals);

    DataApiRequest withFilters(ApiFilters filters);

    DataApiRequest withHavings(Map<LogicalMetric, Set<ApiHaving>> havings);

    DataApiRequest withHaving(Having having);

    DataApiRequest withSorts(LinkedHashSet<OrderByColumn> sorts);

    DataApiRequest withCount(int count);

    DataApiRequest withTopN(int topN);

    DataApiRequest withAsyncAfter(long asyncAfter);

    DataApiRequest withTimeZone(DateTimeZone timeZone);

    DataApiRequest withFilterBuilder(DruidFilterBuilder filterBuilder);
}
