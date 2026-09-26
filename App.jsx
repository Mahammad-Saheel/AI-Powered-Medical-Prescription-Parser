import { BrowserRouter, Routes, Route } from "react-router-dom";

import Dashboard from "./pages/Dashboard";
import UploadPrescription from "./pages/UploadPrescription";
import PrescriptionHistory from "./pages/PrescriptionHistory";
import PrescriptionDetails from "./pages/PrescriptionDetails";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={<Dashboard />}
        />

        <Route
          path="/upload"
          element={<UploadPrescription />}
        />

        <Route
          path="/history"
          element={<PrescriptionHistory />}
        />

        <Route
          path="/prescription/:id"
          element={<PrescriptionDetails />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;