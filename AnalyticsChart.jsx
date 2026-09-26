import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer
} from "recharts";

function AnalyticsChart({ stats }) {

  const data = [
    {
      name: "Prescriptions",
      value: stats.totalPrescriptions
    },
    {
      name: "Medicines",
      value: stats.totalMedicines
    },
    {
      name: "Interactions",
      value: stats.totalInteractions
    }
  ];

  return (
    <div
      style={{
        marginTop: "40px",
        background: "white",
        padding: "20px",
        borderRadius: "10px"
      }}
    >
      <h2>Analytics Overview</h2>

      <ResponsiveContainer
        width="100%"
        height={300}
      >
        <BarChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />

          <XAxis dataKey="name" />

          <YAxis />

          <Tooltip />

          <Bar
            dataKey="value"
          />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
}

export default AnalyticsChart;