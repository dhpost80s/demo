package org.example.matcher;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class DemoTest {

    @Test
    void test() {
        final String EXPECTED_SUBQUERY = String.join("",
                "WHERE tb_movement.accounting_time >= DATE'{DATE_FROM}'",
                "  AND tb_movement.accounting_time < DATE'{DATE_TO}' + 1",
                "GROUP BY");

        final String process = "SELECT /*+ full(tb_movement)" +
                "parallel(tb_movement, 16)" +
                "leading(tb_movement tb_ledger_entry)" +
                "use_nl(tb_ledger_entry tb_movement)" +
                "$StringUtils.join( $hints, $NL )" +
                "*/" +
                "  /********** Dimension columns ***************/" +
                "  $StringUtils.join( $columns, \",$NL  \" )" +
                "  /********** Amount aggregations *************/" +
                "  ," +
                "  $StringUtils.join( $aggregations, \",$NL  \" )" +
                "FROM bn_core.tb_movement" +
                "  JOIN bn_core.tb_ledger_entry ON (tb_ledger_entry.id_movement = tb_movement.id)" +
                "  $StringUtils.join( $joins, \"$NL  \" )" +
                "WHERE tb_movement.accounting_time >= DATE'{DATE_FROM}'" +
                "  AND tb_movement.accounting_time < DATE'{DATE_TO}' + 1" +
                "GROUP BY" +
                "  grouping1," +
                "  grouping2";

        assertThat(process, containsString(EXPECTED_SUBQUERY));
    }
}
