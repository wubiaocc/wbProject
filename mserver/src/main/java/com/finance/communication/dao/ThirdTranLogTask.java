package com.finance.communication.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.Callable;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.finance.communication.common.Function;
import com.finance.communication.common.SpringContextHolder;
import com.finance.communication.entity.ThirdTranLog;

public class ThirdTranLogTask implements Callable<List<ThirdTranLog>> {

	private List<ThirdTranLog> thirdTranLogs;

	public ThirdTranLogTask(List<ThirdTranLog> thirdTranLogs) {
		this.thirdTranLogs = thirdTranLogs;

	}

	@Override
	@Transactional
	public List<ThirdTranLog> call() throws Exception {

		DataSource dataSource = SpringContextHolder.getBean("dataSource");
		final JdbcTemplate temp = new JdbcTemplate(dataSource);

		// 保存交易流水
		String sql = "insert into third_tran_log(id,tran_log_model,tran_log_provider,merchant_name,merchant_no,terminal_no,tran_time,tran_amount,tran_type,"
				+ "merchant_full_name,legal_person,rate,free_amount,terminal_log,batch_no,account_no,bank_log,agent_no,agent_name,tran_desc,tran_mark,create_time,bill_no,"
				+ "mid)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		temp.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int arg1)
					throws SQLException {

				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ThirdTranLog t = thirdTranLogs.get(arg1);

				// 商品代码
				if (StringUtils.isEmpty(t.getId())) {
					String uuid = Function.getUid();
					t.setId(uuid);
				}
				ps.setString(1, t.getId());
				ps.setString(2, t.getTranLogModel());
				ps.setString(3, t.getTranLogProvider());
				ps.setString(4, t.getMerchantName());
				ps.setString(5, t.getMerchantNo());
				ps.setString(6, t.getTerminalNo());
				ps.setString(7, sim.format(t.getTranTime()));
				if (t.getTranAmount() == null) {
					ps.setNull(8, java.sql.Types.INTEGER);
				} else {
					ps.setDouble(8, t.getTranAmount());
				}
				ps.setString(9, t.getTranType());
				ps.setString(10, t.getMerchantFullName());
				ps.setString(11, t.getLegalPerson());
				ps.setString(12, t.getRate());
				if (t.getFreeAmount() == null) {
					ps.setNull(13, java.sql.Types.INTEGER);
				} else {
					ps.setDouble(13, t.getFreeAmount());
				}
				ps.setString(14, t.getTerminalLog());
				ps.setString(15, t.getBatchNo());
				ps.setString(16, t.getAccountNo());
				ps.setString(17, t.getBankLog());
				ps.setString(18, t.getAgentNo());
				ps.setString(19, t.getAgentName());
				ps.setString(20, t.getTranDesc());
				ps.setString(21, t.getTranMark());
				ps.setString(22, sim.format(t.getCreateTime()));
				ps.setString(23, t.getBillNo());
				ps.setString(24, t.getMid());
			}

			@Override
			public int getBatchSize() {
				return thirdTranLogs.size();
			}
		});

		return thirdTranLogs;

	}

}
