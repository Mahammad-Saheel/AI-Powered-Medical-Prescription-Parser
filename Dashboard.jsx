import { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import API from "../services/api";
import AnalyticsChart from "../components/AnalyticsChart";

import {
  Card,
  CardContent,
  Typography,
  Grid,
  Box
} from "@mui/material";

import DescriptionIcon from "@mui/icons-material/Description";
import MedicationIcon from "@mui/icons-material/Medication";
import WarningAmberIcon from "@mui/icons-material/WarningAmber";

function Dashboard() {

  const [stats, setStats] = useState({
    totalPrescriptions: 0,
    totalMedicines: 0,
    totalInteractions: 0
  });

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {

    try {

      const response = await API.get("/api/dashboard/stats");

      setStats(response.data);

    } catch (error) {

      console.error(error);
    }
  };

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />

      <Box
        sx={{
          flex: 1,
          p: 4,
          backgroundColor: "#f4f7fc",
          minHeight: "100vh"
        }}
      >

        <Typography
          variant="h4"
          fontWeight="bold"
          gutterBottom
        >
          🏥 Prescription Analytics Dashboard
        </Typography>

        <Typography
          variant="subtitle1"
          color="text.secondary"
          mb={4}
        >
          Welcome to the AI Prescription Parser System
        </Typography>

        <Grid container spacing={3}>

          <Grid item xs={12} md={4}>
            <Card
              sx={{
                bgcolor: "#1976d2",
                color: "white",
                borderRadius: 3,
                boxShadow: 5
              }}
            >
              <CardContent>

                <DescriptionIcon sx={{ fontSize: 45 }} />

                <Typography variant="h6">
                  Total Prescriptions
                </Typography>

                <Typography variant="h3" fontWeight="bold">
                  {stats.totalPrescriptions}
                </Typography>

              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} md={4}>
            <Card
              sx={{
                bgcolor: "#2e7d32",
                color: "white",
                borderRadius: 3,
                boxShadow: 5
              }}
            >
              <CardContent>

                <MedicationIcon sx={{ fontSize: 45 }} />

                <Typography variant="h6">
                  Total Medicines
                </Typography>

                <Typography variant="h3" fontWeight="bold">
                  {stats.totalMedicines}
                </Typography>

              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} md={4}>
            <Card
              sx={{
                bgcolor: "#ed6c02",
                color: "white",
                borderRadius: 3,
                boxShadow: 5
              }}
            >
              <CardContent>

                <WarningAmberIcon sx={{ fontSize: 45 }} />

                <Typography variant="h6">
                  Drug Interactions
                </Typography>

                <Typography variant="h3" fontWeight="bold">
                  {stats.totalInteractions}
                </Typography>

              </CardContent>
            </Card>
          </Grid>

        </Grid>

        <Card
          sx={{
            mt: 5,
            borderRadius: 3,
            boxShadow: 4
          }}
        >
          <CardContent>

            <Typography
              variant="h5"
              gutterBottom
            >
              📊 Analytics Overview
            </Typography>

            <AnalyticsChart stats={stats} />

          </CardContent>
        </Card>

      </Box>
    </div>
  );
}

export default Dashboard;