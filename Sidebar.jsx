import { Link, useLocation } from "react-router-dom";

import {
  Drawer,
  Toolbar,
  Typography,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText
} from "@mui/material";

import DashboardIcon from "@mui/icons-material/Dashboard";
import UploadFileIcon from "@mui/icons-material/UploadFile";
import HistoryIcon from "@mui/icons-material/History";

const drawerWidth = 250;

function Sidebar() {

  const location = useLocation();

  return (

    <Drawer
      variant="permanent"
      sx={{
        width: drawerWidth,
        flexShrink: 0,

        "& .MuiDrawer-paper": {
          width: drawerWidth,
          boxSizing: "border-box",
          background: "#1565c0",
          color: "white"
        }
      }}
    >

      <Toolbar>

        <Typography
          variant="h6"
          fontWeight="bold"
        >
          🏥 AI Prescription Parser
        </Typography>

      </Toolbar>

      <List>

        <ListItemButton
          component={Link}
          to="/"
          selected={location.pathname === "/"}
        >

          <ListItemIcon>
            <DashboardIcon sx={{ color: "white" }} />
          </ListItemIcon>

          <ListItemText primary="Dashboard" />

        </ListItemButton>

        <ListItemButton
          component={Link}
          to="/upload"
          selected={location.pathname === "/upload"}
        >

          <ListItemIcon>
            <UploadFileIcon sx={{ color: "white" }} />
          </ListItemIcon>

          <ListItemText primary="Upload Prescription" />

        </ListItemButton>

        <ListItemButton
          component={Link}
          to="/history"
          selected={location.pathname === "/history"}
        >

          <ListItemIcon>
            <HistoryIcon sx={{ color: "white" }} />
          </ListItemIcon>

          <ListItemText primary="Prescription History" />

        </ListItemButton>

      </List>

    </Drawer>

  );
}

export default Sidebar;