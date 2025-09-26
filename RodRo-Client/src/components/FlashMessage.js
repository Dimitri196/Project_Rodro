import React, { useState } from "react";

export function FlashMessage({ theme = "info", text, onClose }) {
  const [visible, setVisible] = useState(true);

  if (!visible) return null;

  const handleClose = () => {
    setVisible(false);
    if (onClose) onClose();
  };

  return (
    <div
      className={`alert alert-${theme} alert-dismissible fade show`}
      role="alert"
      style={{ position: "fixed", top: 20, right: 20, zIndex: 1050, minWidth: 250 }}
    >
      {text}
      <button type="button" className="btn-close" aria-label="Close" onClick={handleClose}></button>
    </div>
  );
}

export default FlashMessage;