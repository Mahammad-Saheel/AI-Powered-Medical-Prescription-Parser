import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import Sidebar from "../components/Sidebar";
import API from "../services/api";

import {
  Box,
  TextField,
  Typography,
  Paper,
  Button
} from "@mui/material";

import { DataGrid } from "@mui/x-data-grid";

function PrescriptionHistory() {

  const navigate = useNavigate();

  const [prescriptions, setPrescriptions] = useState([]);
  const [filteredData, setFilteredData] = useState([]);
  const [search, setSearch] = useState("");

  useEffect(() => {
    fetchPrescriptions();
  }, []);

  const fetchPrescriptions = async () => {

    try {

      const response = await API.get("/api/prescriptions");

      setPrescriptions(response.data);
      setFilteredData(response.data);

    } catch (error) {

      console.error(error);
    }
  };

  const handleSearch = (value) => {

    setSearch(value);

    const filtered = prescriptions.filter((prescription) =>

      (prescription.patientName || "")
        .toLowerCase()
        .includes(value.toLowerCase())

      ||

      (prescription.doctorName || "")
        .toLowerCase()
        .includes(value.toLowerCase())

      ||

      (prescription.hospitalName || "")
        .toLowerCase()
        .includes(value.toLowerCase())
    );

    setFilteredData(filtered);
  };

  const columns = [

    {
      field: "id",
      headerName: "ID",
      width: 80
    },

    {
      field: "patientName",
      headerName: "Patient",
      flex: 1
    },

    {
      field: "doctorName",
      headerName: "Doctor",
      flex: 1
    },

    {
      field: "hospitalName",
      headerName: "Hospital",
      flex: 1.5
    },

    {
      field: "prescriptionDate",
      headerName: "Date",
      width: 120
    },

    {
      field: "medicineCount",
      headerName: "Medicines",
      width: 120,
      valueGetter: (_, row) => row.medicines?.length || 0
    },

    {
      field: "action",
      headerName: "Action",
      width: 140,
      sortable: false,
      renderCell: (params) => (
        <Button
          variant="contained"
          size="small"
          onClick={(e) => {
            e.stopPropagation();
            navigate(`/prescription/${params.row.id}`);
          }}
        >
          View
        </Button>
      )
    }

  ];

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
          📜 Prescription History
        </Typography>

        <TextField
          label="Search Patient, Doctor or Hospital"
          variant="outlined"
          fullWidth
          value={search}
          onChange={(e) => handleSearch(e.target.value)}
          sx={{ mb: 3 }}
        />

        <Paper elevation={4} sx={{ height: 650 }}>

          <DataGrid
            rows={filteredData}
            columns={columns}
            pageSizeOptions={[5, 10, 20, 50]}
            initialState={{
              pagination: {
                paginationModel: {
                  pageSize: 10
                }
              }
            }}
            disableRowSelectionOnClick
            onRowClick={(params) =>
              navigate(`/prescription/${params.row.id}`)
            }
            sx={{
              border: 0,
              "& .MuiDataGrid-columnHeaders": {
                backgroundColor: "#1976d2",
                color: "white",
                fontWeight: "bold",
                fontSize: 15
              },
              "& .MuiDataGrid-row:hover": {
                backgroundColor: "#e3f2fd",
                cursor: "pointer"
              }
            }}
          />

        </Paper>

      </Box>

    </div>
  );
}

export default PrescriptionHistory;