import { useState } from "react";
import Sidebar from "../components/Sidebar";
import API from "../services/api";

function UploadPrescription() {

  const [file, setFile] = useState(null);
  const [result, setResult] = useState(null);

  const handleUpload = async () => {

    if (!file) {
      alert("Please select a file");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {

      const response = await API.post(
        "/api/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
      );

      setResult(response.data);

      alert("Prescription uploaded successfully.");

    } catch (error) {

      console.error("Upload Error:", error);

      if (error.response) {

        console.log("Status:", error.response.status);
        console.log("Response:", error.response.data);

        alert(
          "Upload Failed\n\n" +
          "Status: " + error.response.status +
          "\n\n" +
          JSON.stringify(error.response.data, null, 2)
        );

      } else {

        alert("Error: " + error.message);

      }
    }
  };

  return (
    <div style={{ display: "flex" }}>
      <Sidebar />

      <div style={{ padding: "20px", width: "100%" }}>

        <h1>Upload Prescription</h1>

        <input
          type="file"
          onChange={(e) => setFile(e.target.files[0])}
        />

        <br /><br />

        <button onClick={handleUpload}>
          Upload
        </button>

        {result && (
          <div style={{ marginTop: "20px" }}>

            <h2>Prescription Details</h2>

            <p>
              <strong>Doctor:</strong>{" "}
              {result.prescription?.doctorName}
            </p>

            <p>
              <strong>Patient:</strong>{" "}
              {result.prescription?.patientName}
            </p>

            <p>
              <strong>Hospital:</strong>{" "}
              {result.prescription?.hospitalName}
            </p>

            <p>
              <strong>Date:</strong>{" "}
              {result.prescription?.prescriptionDate}
            </p>

            <h3>Medicines</h3>

            {result.prescription?.medicines?.length > 0 ? (
              <ul>
                {result.prescription.medicines.map((medicine) => (
                  <li key={medicine.id}>
                    {medicine.medicineName}
                  </li>
                ))}
              </ul>
            ) : (
              <p>No medicines found.</p>
            )}

            <h3>Drug Interactions</h3>

            {result.interactions?.length > 0 ? (
              <ul>
                {result.interactions.map((interaction, index) => (
                  <li key={index}>
                    <strong>
                      {interaction.drug1} + {interaction.drug2}
                    </strong>
                    {" - "}
                    {interaction.severity}
                    {" - "}
                    {interaction.message}
                  </li>
                ))}
              </ul>
            ) : (
              <p>No drug interactions found.</p>
            )}

          </div>
        )}

      </div>
    </div>
  );
}

export default UploadPrescription;